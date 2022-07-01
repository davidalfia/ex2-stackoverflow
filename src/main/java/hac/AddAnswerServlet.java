package hac;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.event.HyperlinkEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "AddAnswerServlet", value = "/AddAnswerServlet")
public class AddAnswerServlet extends HttpServlet {

    ConcurrentHashMap<Question,Integer> questionStack = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getParameter("questionNumber");


        HttpSession session  = request.getSession();
        session.setAttribute("key",key);

        request.getRequestDispatcher("addAnswer.html").include(request,response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        questionStack =  (ConcurrentHashMap<Question,Integer>)(session.getAttribute("questionStack"));
        String key = (String) session.getAttribute("key");

        String userName = request.getParameter("username");
        String curAnswer = request.getParameter("answer");

        Answer answer = new Answer(userName,curAnswer);

        for(Map.Entry<Question,Integer> entry : questionStack.entrySet())
        {
            if(entry.getValue() == Integer.parseInt(key)){
                entry.getKey().addAnswer(answer);
            }
        }

        request.getRequestDispatcher("index.html").include(request, response);

    }
}

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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

       String key = request.getParameter("questionNumber");

        System.out.println("----get---");
        System.out.println(key);

        HttpSession session = request.getSession();
        session.setAttribute("q",key);

        request.getRequestDispatcher("addAnswer.html").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        questionStack =  (ConcurrentHashMap<Question,Integer>)(session.getAttribute("questionStack"));

        System.out.println("---post---");
        String key = (String) session.getAttribute("q");

        System.out.println(key);


        String userName = request.getParameter("username");
        String curAnswer = request.getParameter("answer");

        Answer answer = new Answer(userName,curAnswer);

        for(Map.Entry<Question,Integer> entry : questionStack.entrySet())
        {
            if(entry.getValue() == 1){
                System.out.println("entry");
                entry.getKey().addAnswer(answer);
            }
        }

        request.getRequestDispatcher("index.html").include(request, response);

    }
}

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

    ConcurrentHashMap<String,Integer> questionStack = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer,Vector<String>> answersStack = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addAnswer.html").include(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        questionStack =  (ConcurrentHashMap<String,Integer>)(session.getAttribute("questionStack"));
        answersStack =  (ConcurrentHashMap<Integer,Vector<String>>)(session.getAttribute("answersStack"));

        Integer questionNumber = Integer.parseInt( request.getParameter("questionNumber"));

        for(Map.Entry<String,Integer> entry : questionStack.entrySet())
        {
            if(entry.getValue() == questionNumber){
                if(answersStack.get(entry.getValue()) != null){
                    if(!answersStack.get(entry.getValue()).contains("answer"))
                        answersStack.get(entry.getValue()).add("answer");
                }
                else {
                    Vector<String> vec = new Vector<String>();
                    vec.add("answer");
                    answersStack.put(entry.getValue(),vec);
                }
            }
        }

    }
}

package hac;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.event.HyperlinkEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


/**
 * add answer servlet. gets the question number by get request from form, send addAnswer.html as response
 * then gets the data from addAnswer.html form. ( username and answer) as post request
 */


@WebServlet(name = "AddAnswerServlet", value = "/AddAnswerServlet")
public class AddAnswerServlet extends HttpServlet {


    /**
     * question stack
     */
    ConcurrentHashMap<Question,Integer> questionStack = new ConcurrentHashMap<>();


    /**
     *
     * @param request  gets question number from index.html form add answer save it in session
     * @param response addAnswer.html that holds the form with username and answer
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getParameter("questionNumber");
        HttpSession session  = request.getSession();
        session.setAttribute("key",key);
        request.getRequestDispatcher("addAnswer.html").include(request,response);

    }

    /**
     *
     * @param request from addAnswer.html form with username and answer,insert into question stack
     * @param response return index.html with updated question stack after insert new answer
     * @throws ServletException
     * @throws IOException
     */
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

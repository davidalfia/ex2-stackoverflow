package hac;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = {""})
public class HelloServlet extends HttpServlet {

    /**
     *
     * @param request  request survey page using get
     * @param response response is the survey page
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("index.html").include(request, response);
    }

    /**
     *
     * @param request   will call foGet
     * @param response  will call doGet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    public void destroy(){

    }
}
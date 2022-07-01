package hac;

import com.google.gson.JsonArray;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Fetch answers.
 */


@WebServlet(name = "fetchAnswers", value = "/fetchAnswers" )
public class FetchAnswers extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext context = request.getServletContext();
        if (context.getAttribute("answers") instanceof AnswerMap) {
            AnswerMap myMap = (AnswerMap) context.getAttribute("answers");
            HashMap<String, ArrayList<String>> localAnsMap = myMap.getMap();
            JsonArray json = new JsonArray();
            for(Map.Entry<String, ArrayList<String>> entry : localAnsMap.entrySet()){
                json.add(entry.getKey());
                json.add(String.valueOf(entry.getValue()));
            }
            response.setContentType("application/Json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
    }

}
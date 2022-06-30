package hac;

import net.maritimecloud.internal.core.javax.json.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "QuestionsServlet", value = "/QuestionsServlet")
public class QuestionsServlet extends HttpServlet {

    ConcurrentHashMap<Question,Integer> questionStack = new ConcurrentHashMap<>();

    @Override
    public void init(){
        Integer idx = 0;
        String filename = getServletContext().getInitParameter("QUESTIONS");
        String filepath = getServletContext().getRealPath(filename);
        try(BufferedReader buffer = new BufferedReader(new FileReader(filepath))) {

            String Line;
            while((Line = buffer.readLine())!=null && Line.length()!=0){
                Question question = new Question(Line, idx++);
                questionStack.put(question,idx);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static private JsonObject setQuestionsJson(Question question, String key){
        JsonObjectBuilder questionJson = Json.createObjectBuilder();
        questionJson.add("question",question.getQuestion());
        questionJson.add("key",key);
        questionJson.add("answersNumber",question.getNumberOfAnswers());
        questionJson.add("answers",question.getAnswers());
        return questionJson.build();
    }


    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder jsonQuestions = Json.createArrayBuilder();

        questionStack.forEach((key,value) -> jsonQuestions.add(setQuestionsJson(key,value.toString())));

        builder.add("questionStack",jsonQuestions.build());

        request.getSession().setAttribute("questionStack",questionStack);

        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonWriter = Json.createWriter(out);
            jsonWriter.writeObject(builder.build());
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer v = Integer.parseInt( request.getParameter("questionNumber"));


    }



}

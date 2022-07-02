package hac;

import net.maritimecloud.internal.core.javax.json.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


/**
 * main servlet, the first one the program will go to
 * in this servlet, in init function we will read the question from file only once
 * then create a question stack in server, hold it in session
 */
@WebServlet(name = "QuestionsServlet", value = "/QuestionsServlet")
public class QuestionsServlet extends HttpServlet {

    ConcurrentHashMap<Question,Integer> questionStack = new ConcurrentHashMap<>();

    /**
     * read question from file only once
     */
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


    /**
     *
     * @param question the question in order to create a json question before sending to client
     * @param key the key in order to create a json key before sending to client
     * @return json object with question,key,amount of answers, and answers vector
     */
    static private JsonObject setQuestionsJson(Question question, String key){
        JsonObjectBuilder questionJson = Json.createObjectBuilder();
        questionJson.add("question",question.getQuestion());
        questionJson.add("key",key);
        questionJson.add("answersNumber",question.getNumberOfAnswers());
        questionJson.add("answers",question.getAnswers());
        return questionJson.build();
    }


    /**
     *
     * @param request fetch from client only once in the program, fetching the question stack
     * @param response return index.html with the questions as json, client side will display on page
     *  json is:  json.questionsStack  ->holds jsonArray of the questions
     */
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


    /**
     *
     * @param request from index.html show answer form, will get the question id
     *                will get the answers to that question and build json holding the answers
     * @param response return json of the answers to requested question to client side
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObjectBuilder questionJson = Json.createObjectBuilder();

        String key = request.getReader().readLine().replace("=", "");
        for(Map.Entry<Question,Integer> entry : questionStack.entrySet())
        {
            if(entry.getValue() == Integer.parseInt(key)){
                questionJson.add("answers",entry.getKey().getAnswers());
            }
        }

        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonWriter = Json.createWriter(out);
            jsonWriter.writeObject(questionJson.build());
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }



}

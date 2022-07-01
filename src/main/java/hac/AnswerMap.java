package hac;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Answer map.
 */
public class AnswerMap {
    private final HashMap<String, ArrayList<String>> questionMap = new HashMap<>();

    /**
     * Gets map.
     *
     * @return the map
     */
    public HashMap<String, ArrayList<String>> getMap() {
        return questionMap;
    }

    /**
     * Build map.
     *
     * @param question the question
     * @param answer   the answer
     */
    public void buildMap(String question, String answer) {
        ArrayList<String> answers = new ArrayList<>();
        if(questionMap.get(question) != null){
            answers = questionMap.get(question);
        }
        answers.add(answer);
        questionMap.put(question, answers);
        System.out.println(this.questionMap);
    }
}

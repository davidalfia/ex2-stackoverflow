package hac;

import net.maritimecloud.internal.core.javax.json.*;

import java.util.Vector;

public class Question {

    private final String question;
    private final Integer key;
    private Vector<Answer> answers;


    public Question(String Question,Integer Key) {
        if ((Question == null || Question.length() == 0)) {
                throw new IllegalArgumentException("must not be empty");
        }

        this.question = Question;
        this.key = Key;
        this.answers = new Vector<Answer>();
    }


    public String getQuestion() {
        return question;
    }

    public Integer getQuestionKey(){
        return key;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void setAnswers(Vector<Answer> answers) {
        this.answers = answers;
    }

    public Integer getNumberOfAnswers() {
        return this.answers.size();
    }

    public JsonArrayBuilder getAnswers(){
        JsonArrayBuilder jsonAnswers = Json.createArrayBuilder();
        for(Answer answer : this.answers)
        {
            JsonObjectBuilder json = Json.createObjectBuilder();
            json.add("username",answer.getUsername());
            json.add("answer",answer.getAnswer());
            jsonAnswers.add(json);
        }
        return jsonAnswers;
    }
}

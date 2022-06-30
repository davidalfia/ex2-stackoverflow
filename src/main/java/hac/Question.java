package hac;

import java.util.Vector;

public class Question {

    private final String question;
    private final Integer key;
    private Vector<String> answers;


    public Question(String Question,Integer Key) {
        if ((Question == null || Question.length() == 0)) {
                throw new IllegalArgumentException("must not be empty");
        }

        this.question = Question;
        this.key = Key;
    }


    public String getQuestion() {
        return question;
    }

    public Integer getKey(){
        return key;
    }

    public void addAnswer(String answer){
        answers.add(answer);
    }

    public void setAnswers(Vector<String> answers) {
        this.answers = answers;
    }
}

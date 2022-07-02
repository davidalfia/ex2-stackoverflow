package hac;

/**
 *  answer class that holds username and the answer to display in index.html
 */
public class Answer {

    private String username;
    private String answer;

    public Answer(String UserName, String Answer){
        if ((UserName == null || UserName.length() == 0)
                || Answer == null || Answer.length() == 0) {
            throw new IllegalArgumentException("must not be empty");
        }

        this.username = UserName;
        this.answer = Answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}

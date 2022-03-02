package edu.url.salle.arnau.sf.pp2;

public class Question {

    private final String text;
    private final String answer;

    public Question(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public boolean isCorrect(String userAnswer) {
        return userAnswer.equals(answer);
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }
}

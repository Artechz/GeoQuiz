package edu.url.salle.arnau.sf.pp2;

public class Player {
    private String name;
    private boolean cheater = false;
    private int score = 0;

    public Player(String nickname) {
        name = nickname;
    }

    public void justCheated(boolean didCheat) {
        cheater = didCheat;
    }

    public void addPoint() {
        score++;
    }

    public String getName() {
        return name;
    }

    public boolean isCheater() {
        return cheater;
    }

    public int getScore() {
        return score;
    }
}

package it.polimi.ingsw.utility;

import java.io.Serializable;

public class BestScore implements Serializable{

    private String username;
    private int bestScore;

    public BestScore(String username, int bestScore) {
        this.username = username;
        this.bestScore = bestScore;
    }

    public String getUsername() {
        return username;
    }

    public int getBestScore() {
        return bestScore;
    }
}

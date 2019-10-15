package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.utility.BestScore;

import java.util.ArrayList;
import java.util.HashMap;

public class EndGameNetObject extends NetObject {

    private Leaderboard leaderboard;

    private ArrayList<BestScore> scores;

    public EndGameNetObject(Leaderboard leaderboard, ArrayList<BestScore> scores) {
        super(MessageHeaderEnum.ENDGAME.toString());
        this.leaderboard = leaderboard;
        this.scores=scores;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public ArrayList<BestScore> getScores() {
        return scores;
    }
}

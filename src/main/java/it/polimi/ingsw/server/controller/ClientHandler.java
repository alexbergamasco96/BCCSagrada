package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.util.ArrayList;

public interface ClientHandler{

    /**
     * sends set up (private, public cards and four patterns) to client
     * @param pr private card
     * @param pu public cards
     * @param pa arry of four patterns
     */

    public void sendSetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Integer> pa);

    /**
     * sends response to client
     * @param response net object containing the response
     */

    public void sendResponse(ResponseNetObject response);

    /**
     * sends client game to client
     * @param clientGame contains public game data
     */

    public void sendClientGame(ClientGame clientGame);

    /**
     * sends end game (leaderboard with game scores and ten best scores from db) to client
     * @param leaderboard contains game scores
     * @param scores contains all time best scores
     */

    public void sendEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores);

    /**
     * sends forced object for client reconnection
     * @param username player username
     * @param pr private card
     * @param pu public cards
     */

    public void sendForcedGameStarted(String username, PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu);

    public String getUsername();

    public void setUsername(String username);

    /**
     * sends extracted die from tool 11
     * @param die extracted die
     */

    public void sendDieTool11(Die die);

}


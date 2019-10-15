package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.util.ArrayList;

public interface ClientObserver {

    // methods of user interface (CLI or GUI)

    /**
     * shows successfull login
     * @param username player username
     */

    public void onSuccessfullLogin(String username);

    /**
     * shows successfull sign in
     * @param username player username
     */

    public void onSuccessfullSignIn(String username);

    /**
     * shows exception launched
     * @param exceptionName exception class name
     * @param errorMessage exception message
     */

    public void onExceptionLaunched(String exceptionName, String errorMessage);

    /**
     * show set up
     * @param pr private card
     * @param pu public cards
     * @param pa four patterns
     */

    public void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa);

    /**
     * shows players patterns
     * @param player public players data
     * @param username player name
     */

    public void onPatternReceived(ArrayList<ClientPlayer> player, String username);

    /**
     * shows reserve
     * @param diceToPlace array of reserve dice
     */

    public void onReserveReceived(ArrayList<Die> diceToPlace);

    /**
     * shows clientGame containing public data
     * @param clientGame public data
     */

    public void onClientGameReceived(ClientGame clientGame);

    /**
     * shows if it's your turn
     * @param clientGame public data
     */

    public void isYourTurn(ClientGame clientGame);

    /**
     * shows when it's not your turn
     */

    public void onNotYourTurn();

    /**
     * Show final scores
     * @param leaderboard game final scores
     * @param scores ten all time best scores
     */

    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores);

    /**
     * show data on reconnection
     */

    public void onForcedPatternChoice();

    /**
     * show die color received from tool 11
     * @param die die extracted from tool 11
     */

    public void onDieTool11Received(Die die);

}

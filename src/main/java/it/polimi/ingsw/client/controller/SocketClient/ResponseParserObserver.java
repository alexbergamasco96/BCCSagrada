package it.polimi.ingsw.client.controller.SocketClient;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.netObject.DieTool11NetObject;
import it.polimi.ingsw.netObject.EndGameNetObject;
import it.polimi.ingsw.netObject.ForcedGameStartedNetObject;
import it.polimi.ingsw.netObject.YourTurnNetObject;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.util.ArrayList;

public interface ResponseParserObserver{

    //function on responseObjectParser notifications to notifies UserInterface

    /**
     * notifies setUp received
     * @param pr private card
     * @param pu public cards
     * @param pa four patterns
     * @throws Exception
     */

    void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa) throws Exception;

    /**
     * notifies successfull login
     * @param username player name
     */

    void onSuccessfullLogin(String username);

    /**
     * notifies saved new user
     * @param username player username
     */

    void onSuccessfullSignIn(String username);

    /**
     * notifies exception received
     * @param exceptionName exception name
     * @param errorMessage explanation
     */

    void onExceptionReceived(String exceptionName, String errorMessage);

    /**
     * notifies client game received
     * @param clientGame public game data
     */

    void onClientGameReceived(ClientGame clientGame);

    /**
     * notifies response received from moveDice e useToolCard
     * @param response ack
     */

    void onResponseReceived(ResponseNetObject response);

    /**
     * notifies if is player turn
     * @param turnNetObject
     */

    void onYourTurn(YourTurnNetObject turnNetObject);

    /**
     * notifies the end of the game
     * @param endGameNetObject contains game scores and ten all time best scores
     */

    void onEndGame(EndGameNetObject endGameNetObject);

    /**
     * notifies riconnection
     * @param forcedGameStartedNetObject contains player data
     */

    void onForcedPatternChoice(ForcedGameStartedNetObject forcedGameStartedNetObject);

    /**
     * notifies die received after using tool 11
     * @param dieTool11NetObject contains the die
     */

    void onDieTool11(DieTool11NetObject dieTool11NetObject);




}

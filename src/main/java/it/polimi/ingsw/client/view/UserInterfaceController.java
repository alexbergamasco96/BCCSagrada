package it.polimi.ingsw.client.view;



import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.util.ArrayList;

public interface UserInterfaceController{


    /**
     * performs login action
     * @throws Exception
     */

    void loginAction() throws Exception;

    /**
     * performs sign in action
     * @throws Exception
     */

    void signInAction() throws Exception;

    /**
     * shows an alert
     * @param header alert header
     * @param message alert message
     * @throws Exception
     */

    void startWaitingAlert(String header, String message) throws Exception;

    /**
     * show an error alert for exception
     * @param exceptionName exception name
     * @param errorMessage exception message
     * @throws Exception
     */

    void startErrorDialog(String exceptionName, String errorMessage) throws Exception;

    /**
     * performs move die action
     * @throws Exception
     */

    void moveDieAction() throws Exception;

}
package it.polimi.ingsw.client.controller.SocketClient;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.netObject.*;
import it.polimi.ingsw.netObject.response.ExceptionResponseObject;
import it.polimi.ingsw.netObject.response.LoginResponseObject;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.netObject.response.SignInResponseObject;
import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.controller.Observable;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.util.ArrayList;

public class ResponseObjectParser implements Observable<ResponseParserObserver> {

    private ArrayList<ResponseParserObserver> observers;

    public ResponseObjectParser() {
        this.observers = new ArrayList<>();
    }

    /**
     * parses received object looking at its header and notifies ClientSocket
     * @param object Net object from server
     */

    public void parse(NetObject object){

        if(object.getHeader().equals(MessageHeaderEnum.LOGIN.toString())){
            LoginResponseObject parsedObject = (LoginResponseObject) object;
            if(((LoginResponseObject) object).getResponse().equals(ResponseHeaderEnum.OK.toString())) {
                notifySuccessfullLogin(parsedObject.getUsername());
            }
        }
        else if(object.getHeader().equals(MessageHeaderEnum.SIGNIN.toString())){
            SignInResponseObject parsedObject = (SignInResponseObject) object;
            if(parsedObject.getResponse().equals((ResponseHeaderEnum.OK.toString()))){
                notifySuccessfullSignIn(parsedObject.getUsername());
            }
        }
        else if(object.getHeader().equals(MessageHeaderEnum.EXCEPTION.toString())){
            ExceptionResponseObject parsedObject = (ExceptionResponseObject)object;
            notifyExceptionLaunched(parsedObject.getExceptionName(),parsedObject.getResponse());
        }
        else if(object.getHeader().equals(MessageHeaderEnum.SETUP.toString())){
            SetupNetObject parsedObject = (SetupNetObject)object;
            PatternNumberParser patternNumberParser = new PatternNumberParser();
            ArrayList<Pattern> patterns = patternNumberParser.parse(parsedObject.getPatternNumbers().get(0),parsedObject.getPatternNumbers().get(1), parsedObject.getPatternNumbers().get(2), parsedObject.getPatternNumbers().get(3));

            notifySetupReceived(parsedObject.getPrivateObjectiveCard(),parsedObject.getPublicObjectiveCards(),patterns);
        }
        else if (object.getHeader().equals(MessageHeaderEnum.CLIENTGAME.toString())){
            ClientGameNetObject parsedObject = (ClientGameNetObject)object;
            notifyClientGameReceived(parsedObject.getClientGame());
        }
        else if (object.getHeader().equals(MessageHeaderEnum.RESPONSE.toString())){
            ResponseNetObject parsedObject = (ResponseNetObject) object;
            notifyResponseReceived(parsedObject);
        }
        else if (object.getHeader().equals(MessageHeaderEnum.YOURTURN.toString())){
            YourTurnNetObject parsedObject = (YourTurnNetObject)object;
            notifyYourTurn(parsedObject);
        }
        else if (object.getHeader().equals(MessageHeaderEnum.ENDGAME.toString())){
            EndGameNetObject parsedObject = (EndGameNetObject) object;
            notifyEndGame(parsedObject);
        }
        else if(object.getHeader().equals(MessageHeaderEnum.FORCEDPATTERNCHOICE.toString())){
            ForcedGameStartedNetObject parsedObject = (ForcedGameStartedNetObject) object;
            notifyForcedPatternChoice(parsedObject);
        }
        else if(object.getHeader().equals(MessageHeaderEnum.DIETOOL11.toString())){
            DieTool11NetObject parsedObject = (DieTool11NetObject)object;
            notifyDieTool11(parsedObject);
        }

    }

    //notifications to ClientSocket

    public void notifyDieTool11(DieTool11NetObject dieTool11NetObject){
        for(ResponseParserObserver obs:observers){
            obs.onDieTool11(dieTool11NetObject);
        }
    }

    public void notifySuccessfullLogin(String username){
        for(ResponseParserObserver obs:observers){
            obs.onSuccessfullLogin(username);
        }
    }

    public void notifySuccessfullSignIn(String username){
        for(ResponseParserObserver obs:observers){
            obs.onSuccessfullSignIn(username);
        }
    }

    public void notifyExceptionLaunched(String exceptionName, String exceptionMessage){
        for(ResponseParserObserver obs:observers){
            obs.onExceptionReceived(exceptionName,exceptionMessage);
        }
    }

    public void notifySetupReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa){
        for(ResponseParserObserver obs:observers){
            try {
                obs.onSetUpReceived(pr,pu,pa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyClientGameReceived(ClientGame clientGame){
        for(ResponseParserObserver obs:observers){
            obs.onClientGameReceived(clientGame);
        }
    }

    public void notifyResponseReceived(ResponseNetObject response){
        for(ResponseParserObserver obs:observers){
            obs.onResponseReceived(response);
        }
    }

    public void notifyYourTurn(YourTurnNetObject turnNetObject){
        for(ResponseParserObserver obs:observers){
            obs.onYourTurn(turnNetObject);
        }
    }

    public void notifyEndGame(EndGameNetObject endGameNetObject){
        for(ResponseParserObserver obs:observers){
            obs.onEndGame(endGameNetObject);
        }
    }

    public void notifyForcedPatternChoice(ForcedGameStartedNetObject forcedGameStartedNetObject){
        for(ResponseParserObserver obs:observers){
            obs.onForcedPatternChoice(forcedGameStartedNetObject);
        }
    }


    @Override
    public void addObserver(ResponseParserObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ResponseParserObserver observer) {
        observers.remove(observer);
    }
}

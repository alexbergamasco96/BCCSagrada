package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.controller.Client;
import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.client.view.ClientObserver;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class endGameViewController implements ClientObserver{

    @FXML
    private Label result;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label bestScoreLabel;

    private Client client;
    private String username;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(Client client) {

        this.client = client;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {

                        System.exit(1);
                    }
                });
            }
        });
    }

    @Override
    public void onSuccessfullLogin(String username) {

    }

    @Override
    public void onSuccessfullSignIn(String username) {

    }

    @Override
    public void onExceptionLaunched(String exceptionName, String errorMessage) {

    }

    @Override
    public void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa) {

    }

    @Override
    public void onPatternReceived(ArrayList<ClientPlayer> player, String username) {

    }

    @Override
    public void onReserveReceived(ArrayList<Die> diceToPlace) {

    }

    @Override
    public void onClientGameReceived(ClientGame clientGame) {

    }

    @Override
    public void isYourTurn(ClientGame clientGame) {

    }

    @Override
    public void onNotYourTurn() {

    }

    @Override
    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) {

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                System.exit(1);
            }
        });

        if(leaderboard.getPlacements().get(0).getName().equals(username)){
            result.setText("You WIN!");
        }else {
            result.setText("You lose!");
        }

        String s = new String("");
        for(Placement p:leaderboard.getPlacements()){
            s+=p.getName()+" : "+p.getPoint()+"\n\n";
        }

        scoreLabel.setText(s);

        String bs = new String("");
        for(BestScore score: scores){
            bs+=score.getUsername()+": "+score.getBestScore()+"\n\n";
        }
        bestScoreLabel.setText(bs);

    }

    @Override
    public void onForcedPatternChoice() {

    }

    @Override
    public void onDieTool11Received(Die die) {

    }
}

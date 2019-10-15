package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.controller.*;

import it.polimi.ingsw.client.controller.RMIClient.RMIClient;
import it.polimi.ingsw.client.controller.SocketClient.ClientSocket;
import it.polimi.ingsw.client.view.ClientObserver;

import it.polimi.ingsw.client.view.UserInterfaceController;
import it.polimi.ingsw.exception.*;

import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class GUIController implements UserInterfaceController, ClientObserver{
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField ipTextField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameSignInTextField;

    @FXML
    private PasswordField passwordSignInTextField;

    @FXML
    private TextField ipSignInTextField;

    @FXML
    private Button signInButton;

    @FXML
    private ToggleGroup connectionRadio;

    @FXML
    private RadioButton socketRadioSelection;

    @FXML
    private RadioButton rmiRadioSelection;

    @FXML
    public GridPane PatternScelta1;

    @FXML
    public GridPane PatternScelta2;

    @FXML
    public GridPane PatternScelta3;

    @FXML
    public GridPane PatternScelta4;



    private final static int SOCKETPORT=1996;
    private final static int RMIPORT = 1099;

    private Client client;


    private String username;

    private Parent root = null;


    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    Boolean active = true;

    @FXML
    public void loginAction() throws Exception {

        if(socketRadioSelection.isSelected()){
            try {
                client = new ClientSocket(ipTextField.getText(), SOCKETPORT);
            } catch (Exception e) {
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                return;
            }
            client.addObserver(this);
        }else if (rmiRadioSelection.isSelected()){
            client = new RMIClient(ipTextField.getText(), RMIPORT);
            try {
                client.connectToServer();
            }catch (Exception e){
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                return;
            }
            client.addObserver(this);
        }

        try {
            client.login(usernameTextField.getText(), passwordTextField.getText());
        } catch (AlreadyLoggedException e) {
        } catch (GameFullException e) {
        } catch (WrongPasswordException e) {
        } catch (NoUserException e) {
        }




    }

    @FXML
    public void signInAction() {

        String rb = new String();

        if(socketRadioSelection.isSelected()){
            try {
                client = new ClientSocket(ipTextField.getText(), SOCKETPORT);
            } catch (Exception e) {
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                return;
            }
            client.addObserver(this);
        }else if (rmiRadioSelection.isSelected()){
            client = new RMIClient(ipTextField.getText(), RMIPORT);
            try {
                client.connectToServer();
            }catch (Exception e){
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                return;
            }
            client.addObserver(this);
        }

        try {
            client.signIn(usernameSignInTextField.getText(),passwordSignInTextField.getText());
        } catch (AlreadyUsedUsernameException e) {
            e.printStackTrace();
        }

    }

    public void rulesAction(){

        openWebPage("http://www.craniocreations.it/wp-content/uploads/2018/02/SAGRADA-regolamento.pdf");

    }

    public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
        }
    }


    public void startErrorDialog(String exceptionName, String exceptionMessage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setTitle("Exception");
                errorDialog.setHeaderText("Error, "+exceptionName+" !");
                errorDialog.setContentText(exceptionMessage);
                errorDialog.showAndWait();
            }
        });




    }

    @Override
    public void moveDieAction() throws Exception {

    }


    public void startWaitingAlert(String header, String message) throws Exception {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(header);
                alert.setContentText(message);
                alert.show();
            }
        });



    }


    @Override
    public void onSuccessfullLogin(String username) {
        this.username=username;
        try {
            //startWaitingAlert();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patternSelection.fxml"));

                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ((PatternChoiceController)loader.getController()).setStage(stage);
                        active=false;
                        client.addObserver((PatternChoiceController)loader.getController());
                        ((PatternChoiceController) loader.getController()).setClient(client);
                        ((PatternChoiceController) loader.getController()).setUsername(username);
                        stage.setTitle("Sagrada - Pattern Choice");

                        stage.setScene(new Scene(root));

                        stage.setFullScreen(true);

                        stage.show();

                    }
                }
        );

    }

    @Override
    public void onExceptionLaunched(String exceptionName, String errorMessage) {
        if(active) {
            try {
                startErrorDialog(exceptionName, errorMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores){

    }

    @Override
    public void onForcedPatternChoice() {

        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameView.fxml"));

                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ((GameViewController)loader.getController()).setStage(stage);
                        active=false;
                        client.addObserver((GameViewController)loader.getController());
                        ((GameViewController) loader.getController()).setClient(client);
                        ((GameViewController) loader.getController()).setUsername(client.getUsername());

                        stage.setTitle("Sagrada");

                        stage.setScene(new Scene(root));

                        stage.setFullScreen(true);

                        stage.show();
                    }
                }
        );
    }

    @Override
    public void onSuccessfullSignIn(String username) {
        try {
            startWaitingAlert("Successfull SignIn !","Ti sei Registrato con successo, loggati per giocare !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDieTool11Received(Die die) {

    }
}

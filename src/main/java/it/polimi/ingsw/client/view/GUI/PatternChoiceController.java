package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.controller.*;
import it.polimi.ingsw.client.view.ClientObserver;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;
import it.polimi.ingsw.utility.GameConfig;
import it.polimi.ingsw.utility.GsonReader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import com.sun.javafx.tk.Toolkit.Task;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PatternChoiceController implements ClientObserver {

    @FXML
    public GridPane PatternScelta1;

    @FXML
    public GridPane PatternScelta2;

    @FXML
    public GridPane PatternScelta3;

    @FXML
    public GridPane PatternScelta4;

    @FXML
    private Image image;

    @FXML
    public ImageView privateCard;

    @FXML
    public ImageView publicCard1;

    @FXML
    public ImageView publicCard2;

    @FXML
    public ImageView publicCard3;

    @FXML
    public RadioButton pattern1RadioSelection;

    @FXML
    public RadioButton pattern2RadioSelection;

    @FXML
    public RadioButton pattern3RadioSelection;

    @FXML
    public RadioButton pattern4RadioSelection;

    @FXML
    public Label name1;

    @FXML
    public Label difficulty1;

    @FXML
    public Label name2;

    @FXML
    public Label difficulty2;

    @FXML
    public Label name3;

    @FXML
    public Label difficulty3;

    @FXML
    public Label name4;

    @FXML
    public Label difficulty4;

    @FXML
    public Label labelSelection;

    @FXML
    public Button confirmButton;

    @FXML
    private ProgressBar progressBar;


    private final static int SOCKETPORT=1996;
    private final static int RMIPORT = 1099;

    private Client client;
    private String username;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    PrivateObjectiveCard privateObjectiveCard;
    ArrayList<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();
    ArrayList<Pattern> patterns = new ArrayList<>();

    PatternInit patternInit = new PatternInit();
    CardInit cardInit = new CardInit();




    public void setClient(Client client) {

        this.client = client;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public void setPatterns(ArrayList<Pattern> patterns){
        this.patterns= patterns;
    }

    public ArrayList<Pattern> getPatterns(){
        return patterns;
    }

    Parent root = null;

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
    public void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa) {

        client.setPrivateObjectiveCard(pr);



        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                String uri = new File("src/main/resources/sounds/churchbell.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();

                cardInit.privateCardInitialization(pr, privateCard);
                cardInit.publicCardInitialization(pu.get(0), publicCard1);
                cardInit.publicCardInitialization(pu.get(1), publicCard2);
                cardInit.publicCardInitialization(pu.get(2), publicCard3);
                fillPattern(pa);

                confirmButton.setDisable(false);

                labelSelection.setText("Scegli uno dei 4 Pattern\n"+"\nLe Vetrate hanno difficoltà variabile dal livello 3\nal livello 6 come indicato sotto il nome.\n"+"\nPattern più difficili valgono più segnalini Favore.");

                GameConfig gameConfig = new GameConfig();
                gameConfig.setGameConfig();

                IntegerProperty seconds = new SimpleIntegerProperty();
                progressBar.progressProperty().bind(seconds.divide((float)gameConfig.getPATTERNCHOICE_TIMEOUT()/1000));
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(seconds, gameConfig.getPATTERNCHOICE_TIMEOUT()/1000)),
                        new KeyFrame(Duration.seconds(gameConfig.getPATTERNCHOICE_TIMEOUT()/1000), e-> {
                            // do anything you need here on completion...
                        }, new KeyValue(seconds, 0))
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

            }
        });

        setPatterns(pa);

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
                        client.removeAllObservers();
                        client.addObserver((GameViewController)loader.getController());
                        ((GameViewController) loader.getController()).setClient(client);
                        ((GameViewController) loader.getController()).setUsername(username);
                    }
                }
        );

    }


    private void fillPattern(ArrayList<Pattern> pa){

        patternInit.patternSelection(pa.get(0), PatternScelta1);
        patternInit.patternSelection(pa.get(1), PatternScelta2);
        patternInit.patternSelection(pa.get(2), PatternScelta3);
        patternInit.patternSelection(pa.get(3), PatternScelta4);

        name1.setText(pa.get(0).getName());
        difficulty1.setText("Difficulty: "+pa.get(0).getDifficulty());
        name2.setText(pa.get(1).getName());
        difficulty2.setText("Difficulty: "+pa.get(1).getDifficulty());
        name3.setText(pa.get(2).getName());
        difficulty3.setText("Difficulty: "+pa.get(2).getDifficulty());
        name4.setText(pa.get(3).getName());
        difficulty4.setText("Difficulty: "+pa.get(3).getDifficulty());

    }

    @Override
    public void onSuccessfullLogin(String username) {

    }


    @Override
    public void onExceptionLaunched(String exceptionName, String errorMessage) {

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

    }

    @Override
    public void onForcedPatternChoice() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setTitle("Sagrada");

                stage.setScene(new Scene(root));

                stage.setFullScreen(true);

                stage.show();
            }
        });
    }

    public void choiceButton(){

        if(pattern1RadioSelection.isSelected()){
            client.patternSelected(patterns.get(0));
        }else if (pattern2RadioSelection.isSelected()){
            client.patternSelected(getPatterns().get(1));
        }else if (pattern3RadioSelection.isSelected()){
            client.patternSelected(getPatterns().get(2));
        }else if (pattern4RadioSelection.isSelected()){
            client.patternSelected(getPatterns().get(3));
        }

        stage.setTitle("Sagrada");

        stage.setScene(new Scene(root));

        stage.setFullScreen(true);

        stage.show();

    }

    @Override
    public void onSuccessfullSignIn(String username) {

    }

    @Override
    public void onDieTool11Received(Die die) {

    }

}

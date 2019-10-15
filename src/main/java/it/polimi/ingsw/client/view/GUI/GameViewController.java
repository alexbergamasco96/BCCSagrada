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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameViewController implements ClientObserver{

    private Client client;
    public void setClient(Client client) { this.client = client; }

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String username;
    public void setUsername(String username) {

        this.username = username;

    }

    private final static int NDICE=9;
    private final static int DIM_DICE=80;

    @FXML
    public ImageView priCard;

    @FXML
    public ImageView pubCard1;

    @FXML
    public ImageView pubCard2;

    @FXML
    public ImageView pubCard3;

    @FXML
    public GridPane Pattern;

    @FXML
    public GridPane Pattern1;

    @FXML
    public GridPane Pattern2;

    @FXML
    public GridPane Pattern3;

    @FXML
    public Label labelRival1;

    @FXML
    public Label labelRival2;

    @FXML
    public Label labelRival3;

    @FXML
    public GridPane Reserve;

    @FXML
    public ImageView die1;

    @FXML
    public ImageView die2;

    @FXML
    public ImageView die3;

    @FXML
    public ImageView die4;

    @FXML
    public ImageView die5;

    @FXML
    public ImageView die6;

    @FXML
    public ImageView die7;

    @FXML
    public ImageView die8;

    @FXML
    public ImageView die9;

    @FXML
    public Label labRound;

    @FXML
    public Label labTurn;

    @FXML
    public Label segnalini;

    @FXML
    public Label console;

    @FXML
    private ProgressBar progressBar;

    @FXML
    public Button endTurnButton;

    @FXML
    public Button toolButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Button plusButton;

    @FXML
    public Button minusButton;

    @FXML
    public Button oneButton;

    @FXML
    public Button twoButton;

    @FXML
    public Button threeButton;

    @FXML
    public Button fourButton;

    @FXML
    public Button fiveButton;

    @FXML
    public Button sixButton;

    @FXML
    public GridPane R1;

    @FXML
    public GridPane R2;

    @FXML
    public GridPane R3;

    @FXML
    public GridPane R4;

    @FXML
    public GridPane R5;

    @FXML
    public GridPane R6;

    @FXML
    public GridPane R7;

    @FXML
    public GridPane R8;

    @FXML
    public GridPane R9;

    @FXML
    public GridPane R10;

    @FXML
    public ImageView toolCard1;

    @FXML
    public ImageView toolCard2;

    @FXML
    public ImageView toolCard3;

    @FXML
    public Label console2;

    @FXML
    public Label segnalini2;

    @FXML
    public ProgressBar progressBar2;

    @FXML
    public ImageView[][] cell;

    @FXML
    private Image image;

    @FXML
    public ImageView[] dice;



    PatternInit patternInit = new PatternInit();
    ReserveInit reserveInit = new ReserveInit();
    CardInit cardInit = new CardInit();

    public ArrayList<Integer> toolCardNumberList;

    private int x;
    private int y;

    public int tmpTimer = 0;

    private ClientGame clientGameLocal;

    boolean soundTurn = false;

    boolean yourTurn = false;

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
    public void onClientGameReceived(ClientGame clientGame) {

        if(clientGameLocal==null){
            clientGameLocal=clientGame;
        }

        if(clientGameLocal.getRoundNumber()!=clientGame.getRoundNumber()&&!clientGame.getActivePlayerUsername().equals(username)){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String uri = new File("src/main/resources/sounds/dieShuffle.m4a").toURI().toString();
                    MediaPlayer player = new MediaPlayer(new Media(uri));
                    player.play();
                }
            });

        }


        clientGameLocal = clientGame;

        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {

                        resetView();

                        fillPattern(clientGame);
                        fillPublicCard(clientGame);
                        fillPrivateCard();
                        fillOtherPatterns(clientGame);
                        fillToolCard(clientGame);
                        moves(clientGame);
                        isYourTurn(clientGame);
                        fillRoundTrack(clientGame);

                        toolClick=false;
                        toolButton.setDisable(true);
                        cancelButton.setDisable(true);
                        plusButton.setDisable(true);
                        minusButton.setDisable(true);
                        oneButton.setDisable(true);
                        twoButton.setDisable(true);
                        threeButton.setDisable(true);
                        fourButton.setDisable(true);
                        fiveButton.setDisable(true);
                        sixButton.setDisable(true);

                        labRound.setText("Round: "+clientGame.getRoundNumber());
                        labTurn.setText("Turn: "+clientGame.getTurnNumber());
                        segnalini.setText("Your Token: "+clientGame.getPlayerFromName(username).getToken());
                        segnalini2.setText("Your Token: "+clientGame.getPlayerFromName(username).getToken());

                        if(tmpTimer!=clientGame.getTurnNumber()){

                            tmpTimer=clientGame.getTurnNumber();

                            GameConfig gameConfig = new GameConfig();
                            gameConfig.setGameConfig();

                            IntegerProperty seconds = new SimpleIntegerProperty();
                            progressBar.progressProperty().bind(seconds.divide((float)gameConfig.getTURN_TIMEOUT()/1000));
                            progressBar2.progressProperty().bind(seconds.divide((float)gameConfig.getTURN_TIMEOUT()/1000));
                            Timeline timeline = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(seconds, gameConfig.getTURN_TIMEOUT()/1000)),
                                    new KeyFrame(Duration.seconds(gameConfig.getTURN_TIMEOUT()/1000), e-> {
                                        // do anything you need here on completion...
                                    }, new KeyValue(seconds, 0))
                            );
                            timeline.setCycleCount(Animation.INDEFINITE);
                            timeline.play();

                        }

                    }
                }
        );

    }

    public void resetView() {

        for (int i = 0; i < NDICE; i++) {
            image = new Image("assets/Card/Constrains/white.jpg", DIM_DICE, DIM_DICE, true, false);
            ImageView imageView = new ImageView(image);
            Reserve.add(imageView, i, 0);
        }

    }

    public void fillPattern(ClientGame clientGame){

        patternInit.patternInitialization(this, clientGame.getYourPattern(username), Pattern,2);

    }

    public void fillOtherPatterns(ClientGame clientGame){

        patternInit.patternInitialization(this, clientGame.getOtherPlayers(username).get(0).getPattern(), Pattern1, 0);
        labelRival1.setText(clientGame.getOtherPlayers(username).get(0).getUsername());
        if(clientGame.getPlayers().size()==3){
            patternInit.patternInitialization(this, clientGame.getOtherPlayers(username).get(1).getPattern(), Pattern2, 0);
            labelRival2.setText(clientGame.getOtherPlayers(username).get(1).getUsername());
        }
        if(clientGame.getPlayers().size()==4){
            patternInit.patternInitialization(this, clientGame.getOtherPlayers(username).get(2).getPattern(), Pattern3, 0);
            labelRival3.setText(clientGame.getOtherPlayers(username).get(2).getUsername());
        }

    }

    public void fillPublicCard(ClientGame clientGame){

        cardInit.publicCardInitialization(clientGame.getPublicObjectiveCards().get(0), pubCard1);
        cardInit.publicCardInitialization(clientGame.getPublicObjectiveCards().get(1), pubCard2);
        cardInit.publicCardInitialization(clientGame.getPublicObjectiveCards().get(2), pubCard3);

    }

    public void fillPrivateCard(){

        cardInit.privateCardInitialization(client.getPrivateObjectiveCard(), priCard);

    }

    public void fillToolCard(ClientGame clientGame){
        cardInit.toolCardInitialization(clientGame.getToolCards().get(0), toolCard1);
        cardInit.toolCardInitialization(clientGame.getToolCards().get(1), toolCard2);
        cardInit.toolCardInitialization(clientGame.getToolCards().get(2), toolCard3);
        toolCardNumberList = new ArrayList<>();
        for(int i=0; i<clientGame.getToolCards().size(); i++){
            toolCardNumberList.add(clientGame.getToolCards().get(i).getNumber());
        }
    }


    public void fillRoundTrack(ClientGame clientGame){

        ArrayList<GridPane> rndTrck = new ArrayList<>();
        rndTrck.add(R1);
        rndTrck.add(R2);
        rndTrck.add(R3);
        rndTrck.add(R4);
        rndTrck.add(R5);
        rndTrck.add(R6);
        rndTrck.add(R7);
        rndTrck.add(R8);
        rndTrck.add(R9);
        rndTrck.add(R10);

        R1.setDisable(true);
        R2.setDisable(true);
        R3.setDisable(true);
        R4.setDisable(true);
        R5.setDisable(true);
        R6.setDisable(true);
        R7.setDisable(true);
        R8.setDisable(true);
        R9.setDisable(true);
        R10.setDisable(true);

        RoundTrack roundTrack = new RoundTrack();
        roundTrack.roundTrack(this, rndTrck, clientGame);

    }

    public void isYourTurn(ClientGame clientGame){

        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  if(clientGame.getActivePlayerUsername().equals(username)){

                                      yourTurn=true;

                                      if (!soundTurn) {

                                          String uri = new File("src/main/resources/sounds/Yourturn.m4a").toURI().toString();
                                          MediaPlayer player = new MediaPlayer(new Media(uri));
                                          player.play();

                                          soundTurn=true;

                                      }
                                      console.setText("It's your turn!");
                                      endTurnButton.setDisable(false);

                                      Reserve.setDisable(false);
                                      Pattern.setDisable(false);

                                      console2.setText("Token per abilitare tool card:\n"+"\nTool Card "+clientGame.getToolCards().get(0).getNumber()+": "+clientGame.getToolCards().get(0).tokenToUse()+ "\n"+
                                              "Tool Card "+clientGame.getToolCards().get(1).getNumber()+": "+clientGame.getToolCards().get(1).tokenToUse()+"\n"+
                                              "Tool Card "+clientGame.getToolCards().get(2).getNumber()+": "+clientGame.getToolCards().get(2).tokenToUse());
                                  }else {
                                      console.setText("Wait your turn!");
                                      endTurnButton.setDisable(true);

                                      Reserve.setDisable(true);
                                      Pattern.setDisable(true);
                                      endTurnButton.setDisable(true);
                                      yourTurn=false;

                                  }

                              }
                          }
        );


    }

    public void endTurn(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String uri = new File("src/main/resources/sounds/click.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();
            }
        });
        soundTurn=false;
        client.endTurn();
    }

    @Override
    public void onNotYourTurn() {

    }

    Parent root = null;

    @Override
    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores){

        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endGameView.fxml"));

                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ((endGameViewController)loader.getController()).setStage(stage);
                        client.removeAllObservers();
                        client.addObserver((endGameViewController)loader.getController());
                        ((endGameViewController) loader.getController()).setClient(client);
                        ((endGameViewController) loader.getController()).setUsername(username);
                        ((endGameViewController) loader.getController()).onEndGame(leaderboard, scores);

                        stage.setTitle("Sagrada - Score");

                        stage.setScene(new Scene(root));

                        stage.setFullScreen(false);

                        String uri = new File("src/main/resources/sounds/churchbell.m4a").toURI().toString();
                        MediaPlayer player = new MediaPlayer(new Media(uri));
                        player.play();

                        stage.show();
                    }
                }
        );

    }

    @Override
    public void onForcedPatternChoice() {

    }



    @FXML
    private void moves(ClientGame clientGame){

        cell = new ImageView[4][5];

        dice = new ImageView[9];

        x=-1; y=-1;

        for(int i=0; i<NDICE; i++) {

            if (clientGame.getReserve().size() >= i + 1) {
                dice[i] = new ImageView(reserveInit.reserveInitialization(clientGame.getReserve().get(i), 1));
                Reserve.add(dice[i], i, 0);
            }

        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(1);
            }
        });

        final GridPane target = Pattern;


            //Drag over event handler is used for the receiving node to allow movement
            target.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //data is dragged over to target
                    //accept it only if it is not dragged from the same node
                    //and if it has image data
                    if(event.getGestureSource() != target && event.getDragboard().hasImage()){
                        //allow for moving
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                }
            });

            //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
            target.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //The drag-and-drop gesture entered the target
                    //show the user that it is an actual gesture target
                    if(event.getGestureSource() != target && event.getDragboard().hasImage()){
                    }
                    event.consume();
                }
            });

            //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
            target.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    event.consume();
                }
            });



            //Drag dropped draws the image to the receiving node
            target.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != target && db.hasImage()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        x = cIndex == null ? 0 : cIndex;
                        y = rIndex == null ? 0 : rIndex;



                        //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
                        //target.add(source, 5, 5, 1, 1);
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        Pattern.add(image, x, y);
                        success = true;

                        console.setText("Dado posizionato nella cella!");
                        console2.setText("Dado posizionato nella cella!");

                    }
                    //let the source know whether the image was successfully transferred and used
                    event.setDropCompleted(success);

                    event.consume();
                }
            });

            //Code optimization try

        /*for(int i=0; i<clientGame.getReserve().size(); i++) {


                int finalI = i;
                dice[finalI].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[finalI].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[finalI].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[finalI].setVisible(false);
                        event.consume();
                    }
                });

                dice[finalI].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[finalI].setVisible(false);
                        }
                        client.moveDie(0, x, y);
                        event.consume();
                    }
                });

                dice[finalI].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva "+(finalI+1)+"\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(finalI);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva "+(finalI+1)+"\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(finalI);
                        }else if(toolNumber==9){
                            console.setText("Scelto dado riserva "+(finalI+1)+"\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(finalI);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva "+(finalI+1)+"\n"+"Scegli il valore del nuovo dado");
                            Reserve.setDisable(false);
                            oneButton.setDisable(false);
                            twoButton.setDisable(false);
                            threeButton.setDisable(false);
                            fourButton.setDisable(false);
                            fiveButton.setDisable(false);
                            sixButton.setDisable(false);
                            param.add(finalI);
                        }
                        event.consume();
                    }
                });

        }*/

        if (clientGame.getReserve().size()>=1) {

            if(toolClick){
                dice[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 1\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 1\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(1);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 1\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 1\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(1);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 1\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 1\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(1);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 1\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 1\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(1);
                        }
                        event.consume();
                    }
                });
            }else{

                dice[0].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[0].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[0].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[0].setVisible(false);
                        event.consume();
                    }
                });

                dice[0].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[0].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(0, x, y);
                        event.consume();
                    }
                });

            }

        }

        if (clientGame.getReserve().size()>=2) {

            if(toolClick){
                dice[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 2\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 2\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(2);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 2\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 2\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(2);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 2\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 2\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(2);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 2\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 2\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(2);
                        }
                        event.consume();
                    }
                });
            }else{
                dice[1].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[1].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[1].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[1].setVisible(false);
                        event.consume();
                    }
                });

                dice[1].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[1].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(1, x, y);
                        event.consume();
                    }
                });
            }

        }

        if (clientGame.getReserve().size()>=3) {

            if(toolClick){
                dice[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 3\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 3\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(3);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 3\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 3\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(3);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 3\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 3\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(3);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 3\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 3\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(3);
                        }
                        event.consume();
                    }
                });
            }else{
                dice[2].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[2].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[2].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[2].setVisible(false);
                        event.consume();
                    }
                });

                dice[2].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[2].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(2, x, y);
                        event.consume();
                    }
                });
            }
        }

        if (clientGame.getReserve().size()>=4) {

            if(toolClick){
                dice[3].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 4\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 4\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(4);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 4\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 4\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(4);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 4\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 4\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(4);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 4\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 4\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(4);
                        }
                        event.consume();
                    }
                });
            }else{

                dice[3].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[3].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[3].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[3].setVisible(false);
                        event.consume();
                    }
                });

                dice[3].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if(event.getTransferMode() == TransferMode.MOVE){
                            dice[3].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(3, x, y);
                        event.consume();
                    }
                });

            }


        }

        if (clientGame.getReserve().size()>=5) {

            if(toolClick){
                dice[4].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)){
                            console.setText("Scelto dado riserva 5\n"+"Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 5\n"+"Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(5);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 5\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 5\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(5);
                        }else if(toolNumber==9){
                            console.setText("Scelto dado riserva 5\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 5\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(5);
                        }else if(toolNumber==8 || toolNumber==11){
                            console.setText("Scelto dado riserva 5\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 5\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(5);
                        }
                        event.consume();
                    }
                });

            }else{

                dice[4].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[4].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[4].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[4].setVisible(false);
                        event.consume();
                    }
                });

                dice[4].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[4].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(4, x, y);
                        event.consume();
                    }
                });

            }

        }

        if (clientGame.getReserve().size()>=6) {

            if(toolClick){
                dice[5].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 6\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 6\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(6);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 6\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 6\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(6);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 6\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 6\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(6);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 6\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 6\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(6);
                        }
                        event.consume();
                    }
                });

            }else{

                dice[5].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[5].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[5].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[5].setVisible(false);
                        event.consume();
                    }
                });

                dice[5].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[5].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(5, x, y);
                        event.consume();
                    }
                });

            }

        }

        if (clientGame.getReserve().size()>=7) {

            if(toolClick){
                dice[6].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)) {
                            console.setText("Scelto dado riserva 7\n" + "Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 7\n" + "Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(7);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 7\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 7\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(7);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 7\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 7\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(7);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 7\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 7\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(7);
                        }
                        event.consume();
                    }
                });
            }else {
                dice[6].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[6].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[6].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[6].setVisible(false);
                        event.consume();
                    }
                });

                dice[6].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[6].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(6, x, y);
                        event.consume();
                    }
                });
            }


        }

        if (clientGame.getReserve().size()>=8) {

            if(toolClick){
                dice[7].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)){
                            console.setText("Scelto dado riserva 8\n"+"Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 8\n"+"Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(8);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 8\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 8\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(8);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 8\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 8\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(8);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 8\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 8\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(8);
                        }
                        event.consume();
                    }
                });

            }else{

                dice[7].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[7].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[7].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[7].setVisible(false);
                        event.consume();
                    }
                });

                dice[7].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[7].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(7, x, y);
                        event.consume();
                    }
                });

            }
        }

        if (clientGame.getReserve().size()>=9) {

            if(toolClick){
                dice[8].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if((toolNumber==1 || toolNumber==6 || toolNumber==10)){
                            console.setText("Scelto dado riserva 9\n"+"Conferma ToolCard con Confirm button");
                            console2.setText("Scelto dado riserva 9\n"+"Conferma ToolCard con Confirm button");
                            toolButton.setDisable(false);
                            param.add(9);
                        }else if(toolNumber==5){
                            console.setText("Scelto dado riserva 9\n"+"Scegli il dado dal Round Track");
                            console2.setText("Scelto dado riserva 9\n"+"Scegli il dado dal Round Track");
                            Reserve.setDisable(true);
                            R1.setDisable(false);
                            R2.setDisable(false);
                            R3.setDisable(false);
                            R4.setDisable(false);
                            R5.setDisable(false);
                            R6.setDisable(false);
                            R7.setDisable(false);
                            R8.setDisable(false);
                            R9.setDisable(false);
                            param.add(9);
                        }else if(toolNumber==8 || toolNumber==9){
                            console.setText("Scelto dado riserva 9\n"+"Scegli la cella del tuo pattern dove\nposizionarlo");
                            console2.setText("Scelto dado riserva 9\n"+"Scegli la cella del tuo pattern dove posizionarlo");
                            Pattern.setDisable(false);
                            cellSelection=true;
                            param.add(9);
                        }else if(toolNumber==11){
                            console.setText("Scelto dado riserva 9\n"+"Conferma e aspetta il nuovo dado");
                            console2.setText("Scelto dado riserva 9\n"+"Conferma e aspetta il nuovo dado");
                            Reserve.setDisable(false);
                            toolButton.setDisable(false);
                            param.add(9);
                        }
                        event.consume();
                    }
                });
            }else {

                dice[8].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //Drag was detected, start drap-and-drop gesture
                        //Allow any transfer node
                        Dragboard db = dice[8].startDragAndDrop(TransferMode.ANY);

                        //Put ImageView on dragboard
                        ClipboardContent cbContent = new ClipboardContent();
                        cbContent.putImage(dice[8].getImage());
                        //cbContent.put(DataFormat.)
                        db.setContent(cbContent);
                        dice[8].setVisible(false);
                        event.consume();
                    }
                });

                dice[8].setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //the drag and drop gesture has ended
                        //if the data was successfully moved, clear it
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            dice[8].setVisible(false);
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String uri = new File("src/main/resources/sounds/putDice.m4a").toURI().toString();
                                MediaPlayer player = new MediaPlayer(new Media(uri));
                                player.play();
                            }
                        });
                        client.moveDie(8, x, y);
                        event.consume();
                    }
                });

            }

        }

    }

    @Override
    public void onDieTool11Received(Die die) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                String dieColor = die.getColor().toString();
                String s = new String();
                switch (dieColor){
                    case "ANSI_RED":
                        s = "Rosso";
                        break;
                    case "ANSI_YELLOW":
                        s = "Giallo";
                        break;
                    case "ANSI_GREEN":
                        s = "Verde";
                        break;
                    case "ANSI_BLUE":
                        s = "Blu";
                        break;
                    case "ANSI_PURPLE":
                        s = "Viola";
                        break;
                }
                toolNumber=11;
                console.setText("Estratto dado colore: "+s+"\n"+"Seleziona valore del nuovo dado");
                console2.setText("Seleziona valore del nuovo dado");
                toolButton.setDisable(true);
                oneButton.setDisable(false);
                twoButton.setDisable(false);
                threeButton.setDisable(false);
                fourButton.setDisable(false);
                fiveButton.setDisable(false);
                sixButton.setDisable(false);
                Reserve.setDisable(true);
                Pattern.setDisable(true);

            }
        });


    }



    public ArrayList<Integer> param;


    public int toolNumber;
    private boolean toolClick;
    public boolean diceSelecetion;
    public boolean cellSelection;

    public boolean dice2Selecetion;
    public boolean cell2Selection;

    public void sendTool(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Pattern.setDisable(false);
                Reserve.setDisable(false);
                if(toolNumber==toolCardNumberList.get(0)){
                    toolNumber=1;
                }else if(toolNumber==toolCardNumberList.get(1)){
                    toolNumber=2;
                }else if(toolNumber==toolCardNumberList.get(2)){
                    toolNumber=3;
                }

                client.useToolCard(toolNumber, param);
                toolButton.setDisable(true);
                endTurnButton.setDisable(false);
                cancelButton.setDisable(true);
                console.setText("Mossa confermata!");
                console2.setText("Mossa confermata!");
                toolClick=false;
                moves(clientGameLocal);

            }
        });

    }

    public void cancel(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String uri = new File("src/main/resources/sounds/click.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();
            }
        });
        toolClick=false;
        moves(clientGameLocal);
        Pattern.setDisable(false);
        Reserve.setDisable(false);
        endTurnButton.setDisable(false);
        plusButton.setDisable(true);
        minusButton.setDisable(true);
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        toolButton.setDisable(true);
        cancelButton.setDisable(true);
        console.setText("Mossa annullata!\n"+"Esegui un' altra operazione");
        console2.setText("Token per abilitare tool card:\n"+"\nTool Card "+clientGameLocal.getToolCards().get(0).getNumber()+": "+clientGameLocal.getToolCards().get(0).tokenToUse()+ "\n"+
                "Tool Card "+clientGameLocal.getToolCards().get(1).getNumber()+": "+clientGameLocal.getToolCards().get(1).tokenToUse()+"\n"+
                "Tool Card "+clientGameLocal.getToolCards().get(2).getNumber()+": "+clientGameLocal.getToolCards().get(2).tokenToUse());
    }

    public void plus(){
        plusButton.setDisable(true);
        minusButton.setDisable(true);
        Reserve.setDisable(false);
        console.setText("Hai scelto di aumentare il valore\n"+"Scegli un dado dalla riserva");
        console2.setText("Hai scelto di aumentare il valore\n"+"Scegli un dado dalla riserva");
        param.add(1);
    }

    public void minus(){
        plusButton.setDisable(true);
        minusButton.setDisable(true);
        Reserve.setDisable(false);
        console.setText("Hai scelto di diminuire il valore\n"+"Scegli un dado dalla riserva");
        console2.setText("Hai scelto di diminuire il valore\n"+"Scegli un dado dalla riserva");
        param.add(2);
    }

    public void one(){
        if(toolNumber==11){
            console.setText("Hai scelto come valore 1\n"+"Conferma valore con Confirm button");
            console2.setText("Hai scelto come valore 1\n"+"Conferma valore con Confirm button");
            oneButton.setDisable(true);
            twoButton.setDisable(true);
            threeButton.setDisable(true);
            fourButton.setDisable(true);
            fiveButton.setDisable(true);
            sixButton.setDisable(true);
            toolButton.setDisable(false);
            client.endTool11(1);
        }else if(toolNumber==12){
            oneButton.setDisable(true);
            twoButton.setDisable(true);
            Pattern.setDisable(false);
            diceSelecetion=true;
            cellSelection=false;
            dice2Selecetion=false;
            cell2Selection=false;
            console.setText("Hai scelto un dado da spostare\n"+"Scegli un dado");
            console2.setText("Hai scelto un dado da spostare\n"+"Scegli un dado");
            param.add(1);
        }

    }

    public void two(){
        if(toolNumber==11){
            console.setText("Hai scelto come valore 2\n"+"Conferma valore con Confirm button");
            console2.setText("Hai scelto come valore 2\n"+"Conferma valore con Confirm button");
            oneButton.setDisable(true);
            twoButton.setDisable(true);
            threeButton.setDisable(true);
            fourButton.setDisable(true);
            fiveButton.setDisable(true);
            sixButton.setDisable(true);
            toolButton.setDisable(false);
            client.endTool11(2);
        }else if(toolNumber==12){
            oneButton.setDisable(true);
            twoButton.setDisable(true);
            Pattern.setDisable(false);
            diceSelecetion=true;
            cellSelection=false;
            dice2Selecetion=false;
            cell2Selection=false;
            console.setText("Hai scelto due dadi da spostare\n"+"Scegli un dado");
            console2.setText("Hai scelto due dadi da spostare\n"+"Scegli un dado");
            param.add(2);
        }
    }

    public void three(){
        console.setText("Hai scelto come valore 3\n"+"Conferma valore con Confirm button");
        console2.setText("Hai scelto come valore 3\n"+"Conferma valore con Confirm button");
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        threeButton.setDisable(true);
        fourButton.setDisable(true);
        fiveButton.setDisable(true);
        sixButton.setDisable(true);
        toolButton.setDisable(false);
        client.endTool11(3);
    }

    public void four(){
        console.setText("Hai scelto come valore 4\n"+"Conferma valore con Confirm button");
        console2.setText("Hai scelto come valore 4\n"+"Conferma valore con Confirm button");
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        threeButton.setDisable(true);
        fourButton.setDisable(true);
        fiveButton.setDisable(true);
        sixButton.setDisable(true);
        toolButton.setDisable(false);
        client.endTool11(4);
    }

    public void five(){
        console.setText("Hai scelto come valore 5\n"+"Conferma valore con Confirm button");
        console2.setText("Hai scelto come valore 5\n"+"Conferma valore con Confirm button");
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        threeButton.setDisable(true);
        fourButton.setDisable(true);
        fiveButton.setDisable(true);
        sixButton.setDisable(true);
        toolButton.setDisable(false);
        client.endTool11(5);
    }

    public void six(){
        console.setText("Hai scelto come valore 6\n"+"Conferma valore con Confirm button");
        console2.setText("Hai scelto come valore 6\n"+"Conferma valore con Confirm button");
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        threeButton.setDisable(true);
        fourButton.setDisable(true);
        fiveButton.setDisable(true);
        sixButton.setDisable(true);
        toolButton.setDisable(false);
        client.endTool11(6);
    }

    public void tc1Action(){
        console.setText("Scelta tool card 1\n"+"\nClicca bottoni per aumentare o\ndimunire il valore del dado");
        console2.setText("Scelta tool card 1\n"+"\nClicca bottoni per aumentare o dimunire il valore del dado");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        plusButton.setDisable(false);
        minusButton.setDisable(false);
        Pattern.setDisable(true);
        Reserve.setDisable(true);
        toolNumber=1;
        param = new ArrayList<>();
    }

    public void tc2Action() {
        console.setText("Scelta tool card 2\n"+"\nScegli dado del tuo pattern da spostare");
        console2.setText("Scelta tool card 2\n"+"\nScegli dado del tuo pattern da spostare");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Pattern.setDisable(false);
        diceSelecetion=true;
        cellSelection=false;
        Reserve.setDisable(true);
        toolNumber=2;
        param = new ArrayList<>();
    }

    public void tc3Action(){
        console.setText("Scelta tool card 3\n"+"\nScegli dado del tuo pattern da spostare");
        console2.setText("Scelta tool card 3\n"+"\nScegli dado del tuo pattern da spostare");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Pattern.setDisable(false);
        diceSelecetion=true;
        cellSelection=false;
        Reserve.setDisable(true);
        toolNumber=3;
        param = new ArrayList<>();
    }

    public void tc4Action(){
        console.setText("Scelta tool card 4\n"+"\nScegli il primo dado che vuoi spostare");
        console2.setText("Scelta tool card 4\n"+"\nScegli il primo dado che vuoi spostare");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Pattern.setDisable(false);
        diceSelecetion=true;
        cellSelection=false;
        dice2Selecetion=false;
        cell2Selection=false;
        Reserve.setDisable(true);
        toolNumber=4;
        param = new ArrayList<>();
    }

    public void tc5Action(){
        console.setText("Scelta tool card 5\n"+"\nScegli un dado dalla riserva");
        console2.setText("Scelta tool card 5\n"+"\nScegli un dado dalla riserva");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Reserve.setDisable(false);
        R1.setDisable(true);
        R2.setDisable(true);
        R3.setDisable(true);
        R4.setDisable(true);
        R5.setDisable(true);
        R6.setDisable(true);
        R7.setDisable(true);
        R8.setDisable(true);
        R9.setDisable(true);
        R10.setDisable(true);
        toolNumber=5;
        param = new ArrayList<>();
    }

    public void tc6Action(){
        console.setText("Scelta tool card 6\n"+"\nScegli un dado da tirare dalla riserva");
        console2.setText("Scelta tool card 6\n"+"\nScegli un dado da tirare dalla riserva");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Reserve.setDisable(false);
        Pattern.setDisable(true);
        toolNumber=6;
        param = new ArrayList<>();
    }

    public void tc7Action(){
        console.setText("Scelta tool card 7\n"+"\nConferma ToolCard con Confirm button");
        console2.setText("Scelta tool card 7\n"+"\nConferma ToolCard con Confirm button");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        toolButton.setDisable(false);
        toolNumber=7;
        param = null;
    }

    public void tc8Action(){
        console.setText("Scelta tool card 8\n"+"\nScegli nuovamente un dado");
        console2.setText("Scelta tool card 8\n"+"\nScegli nuovamente un dado");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Reserve.setDisable(false);
        Pattern.setDisable(true);
        toolNumber=8;
        param = new ArrayList<>();
    }

    public void tc9Action(){
        console.setText("Scelta tool card 9\n"+"\nScegli il dado che vuoi inserire dalla riserva");
        console2.setText("Scelta tool card 9\n"+"\nScegli il dado che vuoi inserire dalla riserva");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Reserve.setDisable(false);
        Pattern.setDisable(true);
        cellSelection=true;
        toolNumber=9;
        param = new ArrayList<>();
    }

    public void tc10Action(){
        console.setText("Scelta tool card 10\n"+"\nScegli il dado dalla riserva");
        console2.setText("Scelta tool card 10\n"+"\nScegli il dado dalla riserva");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Reserve.setDisable(false);
        Pattern.setDisable(true);
        toolNumber=10;
        param = new ArrayList<>();
    }

    public void tc11Action(){
        console.setText("Scelta tool card 11\n"+"\nScegli il dado dalla riserva");
        console2.setText("Scelta tool card 11\n"+"\nScegli il dado dalla riserva");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        Pattern.setDisable(true);
        toolNumber=11;
        param = new ArrayList<>();
    }

    public void tc12Action(){
        console.setText("Scelta tool card 12\n"+"\nSelezionare numero dadi da\nspostare (1 oppure 2)");
        console2.setText("Scelta tool card 12\n"+"\nSelezionare numero dadi da spostare (1 oppure 2)");
        toolClick=true;
        endTurnButton.setDisable(true);
        cancelButton.setDisable(false);
        oneButton.setDisable(false);
        twoButton.setDisable(false);
        Reserve.setDisable(true);
        Pattern.setDisable(true);
        toolNumber=12;
        param = new ArrayList<>();
    }


    public void tool1(){
        if(toolCardNumberList.get(0)==1 && yourTurn){
            tc1Action();
        }else if(toolCardNumberList.get(0)==2 && yourTurn){
            tc2Action();
        }else if(toolCardNumberList.get(0)==3 && yourTurn){
            tc3Action();
        }else if(toolCardNumberList.get(0)==4 && yourTurn){
            tc4Action();
        }else if(toolCardNumberList.get(0)==5 && yourTurn){
            tc5Action();
        }else if(toolCardNumberList.get(0)==6 && yourTurn){
            tc6Action();
        }else if(toolCardNumberList.get(0)==7 && yourTurn){
            tc7Action();
        }else if(toolCardNumberList.get(0)==8 && yourTurn){
            tc8Action();
        }else if(toolCardNumberList.get(0)==9 && yourTurn){
            tc9Action();
        }else if(toolCardNumberList.get(0)==10 && yourTurn){
            tc10Action();
        }else if(toolCardNumberList.get(0)==11 && yourTurn){
            tc11Action();
        }else if(toolCardNumberList.get(0)==12 && yourTurn){
            tc12Action();
        }
        toolClick=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String uri = new File("src/main/resources/sounds/cardPlace.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();
            }
        });
        moves(clientGameLocal);
    }

    public void tool2(){
        if(toolCardNumberList.get(1)==1 && yourTurn){
            tc1Action();
        }else if(toolCardNumberList.get(1)==2 && yourTurn){
            tc2Action();
        }else if(toolCardNumberList.get(1)==3 && yourTurn){
            tc3Action();
        }else if(toolCardNumberList.get(1)==4 && yourTurn){
            tc4Action();
        }else if(toolCardNumberList.get(1)==5 && yourTurn){
            tc5Action();
        }else if(toolCardNumberList.get(1)==6 && yourTurn){
            tc6Action();
        }else if(toolCardNumberList.get(1)==7 && yourTurn){
            tc7Action();
        }else if(toolCardNumberList.get(1)==8 && yourTurn){
            tc8Action();
        }else if(toolCardNumberList.get(1)==9 && yourTurn){
            tc9Action();
        }else if(toolCardNumberList.get(1)==10 && yourTurn){
            tc10Action();
        }else if(toolCardNumberList.get(1)==11 && yourTurn){
            tc11Action();
        }else if(toolCardNumberList.get(1)==12 && yourTurn){
            tc12Action();
        }
        toolClick=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String uri = new File("src/main/resources/sounds/cardPlace.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();
            }
        });
        moves(clientGameLocal);
    }

    public void tool3(){
        if(toolCardNumberList.get(2)==1 && yourTurn){
            tc1Action();
        }else if(toolCardNumberList.get(2)==2 && yourTurn){
            tc2Action();
        }else if(toolCardNumberList.get(2)==3 && yourTurn){
            tc3Action();
        }else if(toolCardNumberList.get(2)==4 && yourTurn){
            tc4Action();
        }else if(toolCardNumberList.get(2)==5 && yourTurn){
            tc5Action();
        }else if(toolCardNumberList.get(2)==6 && yourTurn){
            tc6Action();
        }else if(toolCardNumberList.get(2)==7 && yourTurn){
            tc7Action();
        }else if(toolCardNumberList.get(2)==8 && yourTurn){
            tc8Action();
        }else if(toolCardNumberList.get(2)==9 && yourTurn){
            tc9Action();
        }else if(toolCardNumberList.get(2)==10 && yourTurn){
            tc10Action();
        }else if(toolCardNumberList.get(2)==11 && yourTurn){
            tc11Action();
        }else if(toolCardNumberList.get(2)==12 && yourTurn){
            tc12Action();
        }
        toolClick=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String uri = new File("src/main/resources/sounds/cardPlace.m4a").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(uri));
                player.play();
            }
        });
        moves(clientGameLocal);
    }

    public void pridescr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(client.getPrivateObjectiveCard().getDescription());
        Tooltip.install(priCard, tooltip);
    }

    public void pub1descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getPublicObjectiveCards().get(0).getDescription());
        Tooltip.install(pubCard1, tooltip);
    }

    public void pub2descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getPublicObjectiveCards().get(1).getDescription());
        Tooltip.install(pubCard2, tooltip);
    }

    public void pub3descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getPublicObjectiveCards().get(2).getDescription());
        Tooltip.install(pubCard3, tooltip);
    }

    public void tool1descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getToolCards().get(0).getDescription());
        Tooltip.install(toolCard1, tooltip);
    }

    public void tool2descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getToolCards().get(1).getDescription());
        Tooltip.install(toolCard2, tooltip);
    }

    public void tool3descr(){
        Tooltip tooltip = new Tooltip();
        tooltip.setText(clientGameLocal.getToolCards().get(2).getDescription());
        Tooltip.install(toolCard3, tooltip);
    }


    @Override
    public void onSuccessfullLogin(String username) {

    }

    @Override
    public void onExceptionLaunched(String exceptionName, String errorMessage) {
        try {
            startErrorDialog(exceptionName,errorMessage);
            onClientGameReceived(client.getClientGame());
        } catch (Exception e) {
            e.printStackTrace();
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
                errorDialog.show();
            }
        });
        endTurnButton.setDisable(false);
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
    public void onSuccessfullSignIn(String username) {

    }


}

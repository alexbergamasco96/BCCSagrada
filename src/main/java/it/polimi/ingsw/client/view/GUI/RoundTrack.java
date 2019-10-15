package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.controller.ClientGame;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class RoundTrack {

    private final static int ROUND_NUM = 10;
    private final static int DICE_NUM = 9;

    @FXML
    public ImageView[] dice;

    ReserveInit reserveInit = new ReserveInit();

    public void roundTrack(GameViewController gameViewController, ArrayList<GridPane> rndtrck, ClientGame clientGame) {

        dice = new ImageView[9];

        for (int i = 0; i < ROUND_NUM; i++) {
            for (int j = 0; j < DICE_NUM; j++) {

                if (clientGame.getRoundTrack().getTrack().get(i).size() >= j+1) {
                    dice[j] = new ImageView(reserveInit.reserveInitialization(clientGame.getRoundTrack().getDice(i+1).get(j), 0));
                    int finalI = i;
                    int finalJ = j;
                    dice[j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            if (gameViewController.toolNumber == 5) {
                                gameViewController.console.setText("Scelto dado " + (finalJ+1) + " del Round " + (finalI+1) + "\n" + "Conferma ToolCard con Confirm button");
                                gameViewController.console2.setText("Scelto dado " + (finalJ+1) + " del Round " + (finalI+1) + "\n" + "Conferma ToolCard con Confirm button");
                                gameViewController.R1.setDisable(true);
                                gameViewController.R2.setDisable(true);
                                gameViewController.R3.setDisable(true);
                                gameViewController.R4.setDisable(true);
                                gameViewController.R5.setDisable(true);
                                gameViewController.R6.setDisable(true);
                                gameViewController.R7.setDisable(true);
                                gameViewController.R8.setDisable(true);
                                gameViewController.R9.setDisable(true);
                                gameViewController.R10.setDisable(true);
                                gameViewController.toolButton.setDisable(false);
                                gameViewController.param.add(finalI + 1);
                                gameViewController.param.add(finalJ + 1);
                            }
                            event.consume();
                        }
                    });
                    rndtrck.get(i).add(dice[j], j, 0);
                }

            }

        }

    }
}

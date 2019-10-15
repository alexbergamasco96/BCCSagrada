package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.controller.Client;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.server.model.Pattern;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PatternInit {

    private final static int SMALLCELL = 45;
    private final static int MEDIUMCELL = 70;
    private final static int BIGCELL = 80;

    private final static int SMALLDIE = 40;
    private final static int MEDIUMDIE = 70;

    private final static int ROW = 4;
    private final static int COLUMN = 5;

    private int dimCell;
    private int dimDie;

    public ImageView[][] cell;

    public Image cellImage;

    public Image dieImage;


    private GameViewController gameViewController;

    public void setGameViewController(GameViewController gameViewController) {
        this.gameViewController = gameViewController;
    }


    public void patternSelection(Pattern pattern, GridPane ptr) {

        Platform.runLater(new Runnable() {

            public void run() {

                dimCell = SMALLCELL;

                cell = new ImageView[ROW][COLUMN];

                for (int i = 0; i < ROW; i++) {
                    for (int j = 0; j < COLUMN; j++) {
                        if (pattern.getCell(i, j).getColorConstraint() == null && pattern.getCell(i, j).getNumberConstraint() == 0) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/white.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_RED) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/red.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_YELLOW) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/yellow.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_GREEN) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/green.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_BLUE) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/blue.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_PURPLE) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/purple.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 1) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/one.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 2) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/two.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 3) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/three.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 4) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/four.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 5) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/five.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 6) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/six.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        }

                    }
                }
            }

        });
    }

    public void patternInitialization(GameViewController gameViewController, Pattern pattern, GridPane ptr, int n) {


        setGameViewController(gameViewController);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (n) {
                    case 0:
                        dimCell = SMALLCELL;
                        dimDie = SMALLDIE;
                        break;
                    case 1:
                        dimCell = MEDIUMCELL;
                        dimDie = MEDIUMDIE;
                        break;
                    case 2:
                        dimCell = BIGCELL;
                        dimDie = MEDIUMDIE;
                        break;
                }

                cell = new ImageView[ROW][COLUMN];

                for (int i = 0; i < ROW; i++) {
                    for (int j = 0; j < COLUMN; j++) {
                        if (pattern.getCell(i, j).getColorConstraint() == null && pattern.getCell(i, j).getNumberConstraint() == 0) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/white.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_RED) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/red.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_YELLOW) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/yellow.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_GREEN) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/green.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_BLUE) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/blue.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getColorConstraint() == ColorANSI.ANSI_PURPLE) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/purple.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 1) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/one.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 2) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/two.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 3) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/three.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 4) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/four.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 5) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/five.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        } else if (pattern.getCell(i, j).getNumberConstraint() == 6) {
                            cell[i][j] = new ImageView();
                            cellImage = new Image("assets/Card/Constrains/six.jpg", dimCell, dimCell, true, false);
                            cell[i][j].setImage(cellImage);
                            ptr.add(cell[i][j], j, i);
                        }

                        int finalJ = j;
                        int finalI = i;

                        cell[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                if((gameViewController.toolNumber==2 || gameViewController.toolNumber==3 || gameViewController.toolNumber==8 || gameViewController.toolNumber==9) && gameViewController.cellSelection){
                                    gameViewController.console.setText("Scelto cella colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Conferma con Confirm button");
                                    gameViewController.cellSelection=false;
                                    gameViewController.param.add(finalJ+1);
                                    gameViewController.param.add(finalI+1);
                                    gameViewController.toolButton.setDisable(false);
                                }else if(gameViewController.toolNumber==4 && gameViewController.cellSelection){
                                    gameViewController.console.setText("Scelto cella colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Scegli secondo dado");
                                    gameViewController.cellSelection=false;
                                    gameViewController.param.add(finalJ+1);
                                    gameViewController.param.add(finalI+1);
                                    gameViewController.dice2Selecetion=true;
                                }else if((gameViewController.toolNumber==4 || gameViewController.toolNumber==12) && gameViewController.cell2Selection){
                                    gameViewController.console.setText("Scelto cella colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Conferma con Confirm button");
                                    gameViewController.cell2Selection=false;
                                    gameViewController.param.add(finalJ+1);
                                    gameViewController.param.add(finalI+1);
                                    gameViewController.toolButton.setDisable(false);
                                }else if(gameViewController.toolNumber==12 && gameViewController.cellSelection && gameViewController.param.get(0)==1){
                                    gameViewController.console.setText("Scelto cella colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Conferma con Confirm button");
                                    gameViewController.cellSelection=false;
                                    gameViewController.param.add(finalJ+1);
                                    gameViewController.param.add(finalI+1);
                                    gameViewController.toolButton.setDisable(false);
                                }else if(gameViewController.toolNumber==12 && gameViewController.cellSelection && gameViewController.param.get(0)==2){
                                    gameViewController.console.setText("Scelto cella colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Scegli secondo dado");
                                    gameViewController.cellSelection=false;
                                    gameViewController.param.add(finalJ+1);
                                    gameViewController.param.add(finalI+1);
                                    gameViewController.dice2Selecetion=true;
                                }
                                event.consume();
                            }
                        });

                    }
                }


                for (int i = 0; i < ROW; i++) {
                    for (int j = 0; j < COLUMN; j++) {
                        if (pattern.getCell(i, j).isUsed()) {
                            if (pattern.getCell(i, j).getDie().getNumber() == 1 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/one_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 1 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/one_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 1 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/one_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 1 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/one_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 1 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/one_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 2 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/two_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 2 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/two_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 2 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/two_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 2 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/two_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 2 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/two_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 3 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/three_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 3 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/three_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 3 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/three_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 3 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/three_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 3 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/three_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 4 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/four_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 4 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/four_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 4 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/four_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 4 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/four_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 4 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/four_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 5 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/five_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 5 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/five_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 5 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/five_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 5 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/five_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 5 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/five_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 6 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_RED)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/six_red.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 6 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_YELLOW)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/six_yellow.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 6 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_GREEN)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/six_green.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 6 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_BLUE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/six_blue.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            } else if (pattern.getCell(i, j).getDie().getNumber() == 6 && pattern.getCell(i, j).getDie().getColor().equals(ColorANSI.ANSI_PURPLE)) {
                                cell[i][j] = new ImageView();
                                dieImage = new Image("assets/Die/six_purple.jpg", dimDie, dimDie, true, false);
                                cell[i][j].setImage(dieImage);
                                ptr.add(cell[i][j], j, i);
                                ptr.setConstraints(cell[i][j], j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                            }

                            int finalI = i;
                            int finalJ = j;

                            cell[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent event) {
                                    if((gameViewController.toolNumber==2 || gameViewController.toolNumber==3) && gameViewController.diceSelecetion){
                                        gameViewController.console.setText("Scelto dado colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Scegli nuova posizione pattern");
                                        gameViewController.diceSelecetion=false;
                                        gameViewController.param.add(finalJ+1);
                                        gameViewController.param.add(finalI+1);
                                        gameViewController.cellSelection=true;
                                    }else if((gameViewController.toolNumber==4 || gameViewController.toolNumber==12) && gameViewController.diceSelecetion){
                                        gameViewController.console.setText("Scelto dado colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Scegli nuova posizione pattern");
                                        gameViewController.diceSelecetion=false;
                                        gameViewController.param.add(finalJ+1);
                                        gameViewController.param.add(finalI+1);
                                        gameViewController.cellSelection=true;
                                    }else if((gameViewController.toolNumber==4 || gameViewController.toolNumber==12) && gameViewController.dice2Selecetion){
                                        gameViewController.console.setText("Scelto dado colonna: " + (finalJ+1) + ", riga: " + (finalI+1)+"\n"+"Scegli nuova posizione pattern");
                                        gameViewController.diceSelecetion=false;
                                        gameViewController.param.add(finalJ+1);
                                        gameViewController.param.add(finalI+1);
                                        gameViewController.cell2Selection=true;
                                    }
                                    event.consume();
                                }
                            });

                        }
                    }
                }
            }
        });
    }
}


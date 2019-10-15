package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.utility.ColorANSI;
import javafx.application.Platform;
import javafx.print.PageLayout;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ReserveInit {

    private final static int SMALL=35;
    private final static int BIG=80;

    public ImageView[] cell;

    public Image image;

    public Image reserveInitialization(Die dice, Integer dim){

        switch (dim){
            case 0 :
                dim = SMALL;
                break;
            case 1:
                dim = BIG;
                break;
        }


                            if (dice.getNumber()==1){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){
                                    image = new Image("assets/Die/one_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/one_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/one_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/one_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/one_purple.jpg", dim, dim, true,false);
                                }
                            }else if (dice.getNumber()==2){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){
                                    image = new Image("assets/Die/two_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/two_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/two_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/two_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/two_purple.jpg", dim, dim, true,false);
                                }
                            }if (dice.getNumber()==3){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){
                                    image = new Image("assets/Die/three_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/three_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/three_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/three_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/three_purple.jpg", dim, dim, true,false);

                                }
                            }if (dice.getNumber()==4){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){
                                    image = new Image("assets/Die/four_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/four_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/four_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/four_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/four_purple.jpg", dim, dim, true,false);

                                }
                            }if (dice.getNumber()==5){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){

                                    image = new Image("assets/Die/five_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/five_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/five_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/five_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/five_purple.jpg", dim, dim, true,false);
                                }
                            }if (dice.getNumber()==6){
                                if(dice.getColor().equals(ColorANSI.ANSI_RED)){
                                    image = new Image("assets/Die/six_red.jpg", dim, dim, true, false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_YELLOW)){
                                    image = new Image("assets/Die/six_yellow.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_GREEN)){
                                    image = new Image("assets/Die/six_green.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_BLUE)){
                                    image = new Image("assets/Die/six_blue.jpg", dim, dim, true,false);
                                }else if(dice.getColor().equals(ColorANSI.ANSI_PURPLE)){
                                    image = new Image("assets/Die/six_purple.jpg", dim, dim, true,false);

                                }
                            }

                        return image;

    }

}

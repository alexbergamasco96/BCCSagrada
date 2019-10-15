package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.toolcard.ToolCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CardInit {

    private final static int WIDTH=144;
    private final static int HEIGHT=198;


    public Image image;

    public void privateCardInitialization(PrivateObjectiveCard pr, ImageView priCard) {

        if(pr.getName().equals("Sfumature Rosse")){
            image = new Image("assets/Card/PrivateObjectiveCard/private_red.jpg", 144, 198, false,false);
            priCard.setImage(image);
        }else if(pr.getName().equals("Sfumature Gialle")){
            image = new Image("assets/Card/PrivateObjectiveCard/private_yellow.jpg", 144, 198, false,false);
            priCard.setImage(image);
        }else if(pr.getName().equals("Sfumature Verdi")){
            image = new Image("assets/Card/PrivateObjectiveCard/private_green.jpg", 144, 198, false,false);
            priCard.setImage(image);
        }else if(pr.getName().equals("Sfumature Blu")){
            image = new Image("assets/Card/PrivateObjectiveCard/private_blue.jpg", 144, 198, false,false);
            priCard.setImage(image);
        }else if(pr.getName().equals("Sfumature Viola")){
            image = new Image("assets/Card/PrivateObjectiveCard/private_purple.jpg", 144, 198, false,false);
            priCard.setImage(image);
        }

    }

    public void publicCardInitialization(PublicObjectiveCard pub, ImageView pubImage){

        if(pub.getName().equals("Diagonali Colorate")){
            image = new Image("assets/Card/PublicObjectiveCard/diagonaliColorate.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Varietà di Colore")){
            image = new Image("assets/Card/PublicObjectiveCard/varietaColore.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Colori diversi - Colonna")){
            image = new Image("assets/Card/PublicObjectiveCard/coloriDiversiColonna.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature diverse - Colonna")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureDiverseColonna.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature scure")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureScure.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature diverse")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureDiverse.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature chiare")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureChiare.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature medie")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureMedie.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Colori diversi - Riga")){
            image = new Image("assets/Card/PublicObjectiveCard/coloriDiversiRiga.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }else if(pub.getName().equals("Sfumature diverse - Riga")){
            image = new Image("assets/Card/PublicObjectiveCard/sfumatureDiverseRiga.jpg", WIDTH, HEIGHT, true, true);
            pubImage.setImage(image);
        }


    }

    public void toolCardInitialization(ToolCard tc, ImageView toolCard) {

        if(tc.getNumber()==1){
            image = new Image("assets/Card/ToolCard/pinzaSgrossatrice.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==2){
            image = new Image("assets/Card/ToolCard/pennelloPerEglomise.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==3){
            image = new Image("assets/Card/ToolCard/alesatorePerLaminaDiRame.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==4){
            image = new Image("assets/Card/ToolCard/Lathekin.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==5){
            image = new Image("assets/Card/ToolCard/taglierinaCircolare.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==6){
            image = new Image("assets/Card/ToolCard/pennelloPerPastaSalda.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==7){
            image = new Image("assets/Card/ToolCard/martelletto.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==8){
            image = new Image("assets/Card/ToolCard/tenagliaARotelle.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==9){
            image = new Image("assets/Card/ToolCard/rigaInSughero.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==10){
            image = new Image("assets/Card/ToolCard/tamponeDiamantato.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==11){
            image = new Image("assets/Card/ToolCard/diluentePerPastaSalda.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }else if(tc.getNumber()==12){
            image = new Image("assets/Card/ToolCard/taglierinaManuale.jpg", 144, 198, true,true);
            toolCard.setImage(image);
        }

    }

}

package it.polimi.ingsw;

import it.polimi.ingsw.exception.GameFullException;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GameConfig;
import it.polimi.ingsw.utility.GsonReader;
import it.polimi.ingsw.utility.GsonWriter;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        Database db = Database.getDb();
        //db.executeInsert("CREATE TABLE USERS (ID int AUTO_INCREMENT, USERNAME varchar(50), PASSWORD varchar(50), BEST_SCORE int, PRIMARY KEY(USERNAME, PASSWORD))");
        //db.signIn("Alex","Alex");
        //db.signIn("Fede","Fede");
        //db.signIn("Simo","Simo");
        //db.signIn("Prova","Prova");
        //db.executeInsert("ALTER TABLE USERS ADD LOGGED int");
        //System.out.println(db.sendAction("Test","Test"));
        //System.out.println(db.getID("Test","Test"));
        //System.out.println(db.getBestScore("Test","Test"));
        /*
        db.logout("Test");
        db.logout("Alex");
        db.logout("Fede");
        db.logout("Prova");
        db.logout("Simo");
        db.logout("a");
        db.logout("b");
        db.logout("c");
        db.logout("d");
        db.logout("e");
        db.logout("f");
        db.logout("g");
        db.logout("h");
        */
        //db.reset();






        /*

        Game game = new Game();
        try{
            game.addPlayer(new Player("a", null));
        }catch (GameFullException e){
            e.printStackTrace();
        }
        try{
            game.addPlayer(new Player("b", null));
        }catch (GameFullException e) {
            e.printStackTrace();
        }
*/
    }
}

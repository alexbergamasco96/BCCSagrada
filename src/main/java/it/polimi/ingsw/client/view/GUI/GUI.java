package it.polimi.ingsw.client.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


/*
 * Created by cisla73 on 09/05/18
*/

public class GUI extends Application{

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginView.fxml"));

        Parent root = loader.load();

        ((GUIController)loader.getController()).setStage(primaryStage);

        primaryStage.setTitle("Sagrada - Connection");

        primaryStage.setScene(new Scene(root, 400, 650));

        primaryStage.show();

    }

    public static void main(String[] args) {


        launch(args);



    }

}
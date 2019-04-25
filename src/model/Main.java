package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        GameManager gameManager = new GameManager();
        Scene scene = new Scene(gameManager);
        //System.out.println(scene);
        //scene.getStylesheets().add("/images/Styles.css");
        scene.getStylesheets().add(getClass().getResource("/images/Styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        gameManager.setUpHashSet();
        //System.out.println(gameManager.getScene().getStylesheets());
        primaryStage.setTitle("Dreamscapes");
        primaryStage.show();
    }
}

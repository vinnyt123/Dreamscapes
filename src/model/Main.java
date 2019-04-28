package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    public static final int SCENE_WIDTH = 1440;
    public static final int SCENE_HEIGHT = 900;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        GameManager gameManager = new GameManager();
        Scene scene = new Scene(gameManager);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/images/Icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        gameManager.setUp();
        primaryStage.sizeToScene();
        primaryStage.setTitle("Dreamscapes");
        primaryStage.show();
    }
}

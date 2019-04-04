package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        GameManager gameManager = new GameManager();
        Scene scene = new Scene(gameManager);
        primaryStage.setScene(scene);
        gameManager.setUpHashSet();
        primaryStage.setTitle("Dreamscapes");
        primaryStage.show();
    }
}

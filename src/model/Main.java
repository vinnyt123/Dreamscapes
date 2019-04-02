package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static String mainMenuID = "Main Menu";
    public static String mainMenuFile = "view/MainMenu.fxml";




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        ScreenManager screenManager = new ScreenManager();
        screenManager.loadScreen(Main.mainMenuID, Main.mainMenuFile);

        screenManager.setScreen(Main.mainMenuID);
        primaryStage.setScene(new Scene(screenManager));
        primaryStage.setTitle("Dreamscapes");
        primaryStage.show();
    }
}

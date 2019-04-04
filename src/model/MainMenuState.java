package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class MainMenuState extends Pane {

    public static String mainMenuID = "Main Menu";
    public static String mainMenuFile = "view/MainMenu.fxml";

    private HashMap<String, Node> loadedScreens = new HashMap<>();


    public MainMenuState() {
        loadScreen(mainMenuID, mainMenuFile);
        setScreen(mainMenuID);
    }

    public void loadScreen(String screenName, String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        try {
            loadedScreens.put(screenName, loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScreen(String name) {
        if (loadedScreens.containsKey(name)) {
            if (!this.getChildren().isEmpty()) {
                this.getChildren().clear();
            }
            this.getChildren().add(loadedScreens.get(name));
        } else {
            System.out.println("Screen " + name + " has not been loaded.");
        }
    }
}

package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenManager extends StackPane {

    private HashMap<String, Node> loadedScreens = new HashMap<>();


    public ScreenManager() {
        super();
    }


    public void loadScreen(String screenName, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        loadedScreens.put(screenName, loader.load());
        ControlledScreen screen = (ControlledScreen) loader.getController();
        screen.setScreenParent(this);
    }

    public void setScreen(String name) {
        if (loadedScreens.containsKey(name)) {
            if (!this.getChildren().isEmpty()) {
                this.getChildren().remove(0);
            }
            this.getChildren().add(loadedScreens.get(name));
        } else {
            System.out.println("Screen " + name + " has not been loaded.");
        }
    }




    public void startGame() {

    }
}

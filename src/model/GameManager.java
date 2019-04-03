package model;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class GameManager extends StackPane {

    private HashMap<String, Node> loadedScreens = new HashMap<>();
    private HashMap<String, Node> loadedMaps = new HashMap<>();

    private HashSet<String> keysPressed = new HashSet<String>();
    private Player player = new Player();
    private AnimationTimer gameLoop;
    private Scene scene;
    private Map currentMap;


    public GameManager() {
        super();
    }


    public void loadScreen(String screenName, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        loadedScreens.put(screenName, loader.load());
        ControlledScreen screen = (ControlledScreen) loader.getController();
        screen.setScreenParent(this);
    }

    public void loadMap(String mapID, String mapFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(mapFile));
        loadedMaps.put(mapID, loader.load());
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

    public void setMap(String name) {
        if (loadedMaps.containsKey(name)) {
            if (!this.getChildren().isEmpty()) {
                this.getChildren().remove(0);
            }
            currentMap = new Map(loadedMaps.get(name), player);
            this.getChildren().add(currentMap);

        } else {
            System.out.println("Map " + name + " has not been loaded.");
        }
    }



    public void startGame() {
        setUpHashSet();
        setMap(Main.map0ID);
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                player.performActions(keysPressed);
                currentMap.moveEntities();
            }
        };

        gameLoop.start();
    }

    public void pauseGame() {
        gameLoop.stop();
    }

    public void resumeGame() {
        gameLoop.start();
    }

    private void setUpHashSet() {

        this.getScene().setOnKeyPressed(e -> {
            keysPressed.add(e.getCode().toString());
            System.out.println(e.getCode().toString());
        });

        this.getScene().setOnKeyReleased(e -> {
            keysPressed.remove(e.getCode().toString());
        });
    }


}

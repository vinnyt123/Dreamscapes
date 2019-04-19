package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class PlayingState extends StackPane {

    public static String map0ID = "Map0";
    public static String map0File = "view/Map0.fxml";
    public static String pauseMenuFile = "view/PauseMenu.fxml";

    private HashMap<String, Pane> loadedMaps = new HashMap<>();
    private FXMLLoader loader;
    private HashSet<String> keysPressed;
    private Player player;
    private Node pauseMenuLayer;
    private Pane mapLayer = new Pane();
    private Map currentMap;

    public PlayingState(HashSet<String> keysPressed) {
        this.keysPressed = keysPressed;
        loadMap(map0ID, map0File);
        loadPauseMenu();
        this.getChildren().addAll(mapLayer, pauseMenuLayer);
    }

    public void loadMap(String mapID, String mapFile) {
        loader = new FXMLLoader(getClass().getClassLoader().getResource(mapFile));
        try {
            loadedMaps.put(mapID, loader.load());
        } catch (IOException e) {
            System.out.println("ligma");
            e.printStackTrace();
        }
    }

    private void loadPauseMenu() {
        loader = new FXMLLoader(getClass().getClassLoader().getResource(pauseMenuFile));
        try {
            pauseMenuLayer = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMap(String name, Player player) {
        if (loadedMaps.containsKey(name)) {
            currentMap = new Map(loadedMaps.get(name), player);
            mapLayer.getChildren().clear();
            mapLayer.getChildren().add(currentMap);
        } else {
            System.out.println("Map " + name + " has not been loaded.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void newGame() {
        player = new Player(keysPressed);
        setMap(map0ID, player);
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

}

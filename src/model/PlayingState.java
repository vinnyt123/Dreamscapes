package model;

import controllers.PauseMenuController;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingState extends StackPane {

    public static String map0ID = "Map0";
    public static String map0File = "view/Map0.fxml";
    public static String pauseMenuFile = "view/PauseMenu.fxml";
    public static String map1ID = "Map1";
    public static String map1File = "view/Map1.fxml";

    private PauseMenuController pm;
    private Timer gameTimer;
    private long secondsPassed = 0;
    private HashMap<String, String> mapsMap = new HashMap<>();
    private FXMLLoader loader;
    private FXMLLoader pauseLoader;
    private HashSet<String> keysPressed;
    private Player player;
    private Node pauseMenuLayer;
    private Pane mapLayer = new Pane();
    private Pane loaderRoot;
    private Map currentMap;

    PlayingState(HashSet<String> keysPressed) {
        this.keysPressed = keysPressed;
        loadPauseMenu();
        mapsMap.put(map0ID, map0File);
        mapsMap.put(map1ID, map1File);
        this.getChildren().addAll(mapLayer, pauseMenuLayer);
    }

    private void loadPauseMenu() {
        pauseLoader = new FXMLLoader(getClass().getClassLoader().getResource(pauseMenuFile));
        try {
            pauseMenuLayer = pauseLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pm = pauseLoader.getController();
    }

    private String secondsConverter(long seconds) {
        long minutes = seconds / 60;
        String time = "";
        time += minutes + ":";
        if((seconds - 60 * minutes) < 10) {
            time += "0";
        }
        return time + ((seconds - 60 * minutes));
    }

    void setMap(String name) {
        if(name.equals(map0ID)) {
            secondsPassed = 0;
        }
        loader = new FXMLLoader(getClass().getClassLoader().getResource(mapsMap.get(name)));
        try {
            loaderRoot = loader.load();
            currentMap = new Map(loaderRoot, player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapLayer.getChildren().clear();
        mapLayer.getChildren().add(currentMap);

    }

    public Player getPlayer() {
        return player;
    }

    void newGame() {
        player = new Player(keysPressed);
        setMap(map0ID);
        startTimer();
    }

    void startTimer() {
        gameTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
                Platform.runLater(() -> pm.getTimeCount().setText(secondsConverter(secondsPassed)));
            }
        };
        gameTimer.scheduleAtFixedRate(task, 1000, 1000);
    }

    void pauseTimer() {
            gameTimer.cancel();
    }

    Map getCurrentMap() {
        return currentMap;
    }

    FXMLLoader getPauseLoader() {
        return pauseLoader;
    }

    void restartMap() {
        currentMap = new Map(loaderRoot, player);
        mapLayer.getChildren().clear();
        mapLayer.getChildren().add(currentMap);
    }
}

package model;

import controllers.GameCompleteController;
import controllers.MainMenuController;
import controllers.PauseMenuController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingState extends StackPane {

    private final String PAUSE_KEY = "P";
    private final String EXIT_KEY = "ESCAPE";
    private final String BOSS_LEVEL_KEY = "PAGE_DOWN";
    private final String NEXT_MAP_KEY = "END";
    private final String PREVIOUS_MAP_KEY = "HOME";

    private static String pauseMenuFile = "view/PauseMenu.fxml";
    private static String tutorialID = "Tutorial";
    private static String tutorialFile = "view/TutorialLevel.fxml";
    private static String level1ID = "Level1";
    private static String level1File = "view/Level1.fxml";
    static String bossArenaID = "BossArena";
    private static String bossArenaFile = "view/BossArena.fxml";
    private static String GameOverFile = "view/GameOverScreen.fxml";

    private static final String STARTING_LEVEL = tutorialID;

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
    private Pane gameOverPane;
    private TimerTask task;
    private TaskService service = new TaskService();
    private String mapFrom;
    private String nextMap;

    PlayingState(HashSet<String> keysPressed) {
        this.keysPressed = keysPressed;
        mapLayer.setStyle("-fx-background-color: black;");
        loadPauseMenu();
        mapsMap.put(tutorialID, tutorialFile);
        mapsMap.put(level1ID, level1File);
        mapsMap.put(bossArenaID, bossArenaFile);
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

    long getSecondsPassed() {
        return secondsPassed;
    }

    String secondsConverter(long seconds) {
        long minutes = seconds / 60;
        String time = "";
        time += minutes + ":";
        if((seconds - 60 * minutes) < 10) {
            time += "0";
        }
        return time + ((seconds - 60 * minutes));
    }

    void addGameOverPane() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(GameOverFile));
        try {
            gameOverPane = loader.load();
            GameCompleteController gc = loader.getController();
            gc.setLabels(secondsConverter(getSecondsPassed()), player.deathCount.get());
            this.getChildren().add(gameOverPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void removeGameOver() {
        if (gameOverPane != null) {
            this.getChildren().remove(gameOverPane);
        }
    }

    void setMap(String name, String mapFrom) {
        this.mapFrom = mapFrom;
        this.nextMap = name;
        loader = new FXMLLoader(getClass().getClassLoader().getResource(mapsMap.get(name)));
        service.setOnSucceeded(e -> {
            mapLayer.getChildren().clear();
            currentMap.setOpacity(0);
            mapLayer.getChildren().add(currentMap);
            //Remove and re-add player to ensure they're on top of image view
            currentMap.getChildren().remove(player);
            currentMap.getChildren().add(player);
            if(!(player.isCreatedSprite())) {
                player.createSprite();
            }
            FadeTransition ft = new FadeTransition(Duration.millis(3000), currentMap);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(1);
            ft.play();
            requestFocus();
            GameManager gm = (GameManager) getScene().getRoot();
            gm.startGameLoop();
        });
        FadeTransition ft = new FadeTransition(Duration.millis(3000), currentMap);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
        service.restart();

    }

    public Player getPlayer() {
        return player;
    }

    void newGame() {
        secondsPassed = 0;
        player = new Player(keysPressed);
        setMap(STARTING_LEVEL, null);
        startTimer();
    }

    void startTimer() {
        gameTimer = new Timer();
        task = new TimerTask() {
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
        task.cancel();
    }

    Map getCurrentMap() {
        return currentMap;
    }

    FXMLLoader getPauseLoader() {
        return pauseLoader;
    }

    void restartMap() {
        currentMap = new Map(loaderRoot, player, currentMap.getMapId(), null);
        mapLayer.getChildren().clear();
        mapLayer.getChildren().add(currentMap);
    }

    void checkKeys() {
        if (keysPressed.contains(BOSS_LEVEL_KEY)) {
            keysPressed.remove(BOSS_LEVEL_KEY);
            advanceToBossLevel();
        }

        if (keysPressed.contains(PAUSE_KEY)) {
            pm.pauseGame();
        }

        if (keysPressed.contains(EXIT_KEY)) {
            ((GameManager) this.getScene().getRoot()).switchToMenu();
        }

        if (keysPressed.contains(NEXT_MAP_KEY)) {
            keysPressed.remove(NEXT_MAP_KEY);
            nextMap();
        }

        if (keysPressed.contains(PREVIOUS_MAP_KEY)) {
            keysPressed.remove(PREVIOUS_MAP_KEY);
            previousMap();
        }
    }

    private void nextMap() {
        if (currentMap.getMapId().equals(tutorialID)) {
            setMap(level1ID, null);
        } else if (currentMap.getMapId().equals(level1ID)) {
            player.addDoubleJumpBoots();
            setMap(bossArenaID, null);
        }
    }

    private void previousMap() {
        if (currentMap.getMapId().equals(bossArenaID)) {
            setMap(level1ID, null);
        } else if (currentMap.getMapId().equals(level1ID)) {
            setMap(tutorialID, null);
        }
    }

    private void advanceToBossLevel() {
        player.addDoubleJumpBoots();
        setMap(bossArenaID, currentMap.getMapId());
    }

    void createBootsOverLay() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        Text text = new Text("Double Jump Boots");
        text.setFill(Paint.valueOf("#e1e6ef"));
        text.setStroke(Paint.valueOf("#000000"));
        text.setEffect(dropShadow);

        text.setFont(Font.font(null, FontWeight.BOLD, 60));
        Pane pane = new Pane(text);
        text.setX((Main.SCENE_WIDTH / 2) - 282);
        text.setY(-20);
        this.getChildren().add(pane);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(200), new KeyValue(text.layoutYProperty(),100)));

        Timeline timeline2 = new Timeline();
        timeline2.getKeyFrames().addAll(new KeyFrame(Duration.millis(200), new KeyValue(text.layoutYProperty(),-20)));

        timeline.setOnFinished(e -> {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    timeline2.play();
                }
            };
            new Timer().schedule(timerTask,3000);
        });
        timeline.play();


        timeline2.setOnFinished(e -> this.getChildren().remove(pane));
    }

    private class TaskService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    try {
                        loaderRoot = loader.load();
                        currentMap = new Map(loaderRoot, player, nextMap, mapFrom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
    }

}

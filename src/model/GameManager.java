package model;

import controllers.GameCompleteController;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.*;
import java.util.HashSet;
import java.util.List;

public class GameManager extends StackPane {

    private HashSet<String> keysPressed  = new HashSet<>();
    private AnimationTimer gameLoop;
    private MainMenuState mainMenuState;
    private PlayingState playingState;
    private HighScores highScores;

    public GameManager() {
        super();
    }

    public void switchToMenu() {
        this.getChildren().clear();
        this.getChildren().add(mainMenuState);
        mainMenuState.setScreen(MainMenuState.mainMenuID);
        gameLoop.stop();
    }

    public void switchToPlayingGame() {
        this.getChildren().clear();
        this.getChildren().add(playingState);
        playingState.newGame();
        playingState.getPlayer().createSprite();
        playingState.requestFocus();
        gameLoop.start();
    }



    void restartLevel() {
        playingState.restartMap();
    }

    void endGame() {
        mainMenuState.setOpacity(0.0);
        playingState.pauseTimer();
        FadeTransition ft = new FadeTransition(Duration.millis(3000), mainMenuState);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
        gameLoop.stop();
        playingState.addGameOverPane();
    }

    public void saveScore(String playerName) {

        long secondsPassedNum = playingState.getSecondsPassed();
        String secondsString = playingState.secondsConverter(secondsPassedNum);
        int deathCount = playingState.getPlayer().deathCount.get();
        highScores.addScore(new Score(secondsPassedNum,secondsString,deathCount, playerName));
    }

    public PlayingState getPlayingState() {
        return playingState;
    }

    public void pauseGame() {
        playingState.pauseTimer();
        gameLoop.stop();
    }

    public void resumeGame() {
        playingState.requestFocus();
        playingState.startTimer();
        gameLoop.start();
    }

    private void setUpGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                playingState.getCurrentMap().moveEntities();
                playingState.checkKeys();
            }
        };
    }

    public void setUpHashSet() {

        this.setOnKeyPressed(e -> {
            keysPressed.add(e.getCode().toString());
        });

        this.setOnKeyReleased(e -> keysPressed.remove(e.getCode().toString()));
    }

    public void setUp() {
        mainMenuState = new MainMenuState(this);
        playingState = new PlayingState(keysPressed);
        setUpGameLoop();
        switchToMenu();
        setUpHashSet();
        deserializeHighScores();
    }

    private void deserializeHighScores() {
        try {
            File file = new File("employee.ser");
            if (!file.exists()) {
                highScores = new HighScores();
            } else {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                highScores = (HighScores) in.readObject();
                in.close();
                fileIn.close();
                highScores.printScores();
            }
        } catch (IOException i) {
            highScores = new HighScores();
            System.out.println("Please delete the file employee.ser.");
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    public void serializeHighScores() {
        try {
            if (!highScores.isEmpty()) {
                //The following line of code gets the path to the jar so may work on both windows and linux
                //MyClass.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                File file = new File("employee.ser");
                /*if (!file.exists()) {
                    file.createNewFile();
                }*/

                FileOutputStream fileOut =
                        new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(highScores);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in /tmp/employee.ser");
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public MainMenuState getMainMenuState() {
        return mainMenuState;
    }

    public HighScores getHighscores() {
        return highScores;
    }
}

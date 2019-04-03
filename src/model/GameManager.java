package model;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

import java.util.HashSet;

public class GameManager {

    private HashSet<String> keysPressed = new HashSet<String>();
    private Scene scene;
    private Player player = new Player();


    private AnimationTimer gameLoop;

    public GameManager(Scene scene) {

        this.scene = scene;
        setUpHashSet();
        startGame();
    }

    public void startGame() {
        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                player.performActions(keysPressed);
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

        scene.setOnKeyPressed(e -> {
            keysPressed.add(e.getCode().toString());
            System.out.println(e.getCode().toString());
        });

        scene.setOnKeyReleased(e -> {
            keysPressed.remove(e.getCode().toString());
        });
    }
}

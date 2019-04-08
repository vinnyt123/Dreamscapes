package model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import java.util.HashSet;

public class GameManager extends StackPane {

    private HashSet<String> keysPressed = new HashSet<String>();
    private Player player = new Player(keysPressed);
    private AnimationTimer gameLoop;
    private MainMenuState mainMenuState;
    private PlayingState playingState;

    public GameManager() {
        super();
        mainMenuState = new MainMenuState();
        playingState = new PlayingState(player);
        setUpGameLoop();
        switchToMenu();
    }

    public void switchToMenu() {
        this.getChildren().clear();
        this.getChildren().add(mainMenuState);
        gameLoop.stop();
    }

    public void switchToPlayingGame() {
        this.getChildren().clear();
        this.getChildren().add(playingState);
        gameLoop.start();
    }

    public void pauseGame() {
        gameLoop.stop();
    }

    public void resumeGame() {
        gameLoop.start();
    }

    private void setUpGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                playingState.getCurrentMap().moveEntities();
            }
        };
    }

    public void setUpHashSet() {
        this.setOnKeyPressed(e -> {
            keysPressed.add(e.getCode().toString());
            System.out.println(e.getCode().toString());
        });
        this.setOnKeyReleased(e -> keysPressed.remove(e.getCode().toString()));
    }

}

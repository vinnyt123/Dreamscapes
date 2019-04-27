package model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import java.util.HashSet;

public class GameManager extends StackPane {

    private HashSet<String> keysPressed  = new HashSet<>();
    private AnimationTimer gameLoop;
    private MainMenuState mainMenuState;
    private PlayingState playingState;

    public GameManager() {
        super();
    }

    public void switchToMenu() {
        this.getChildren().clear();
        this.getChildren().add(mainMenuState);
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


    public PlayingState getPlayingState() {
        return playingState;
    }

    public void pauseGame() {
        playingState.pauseTimer();
        gameLoop.stop();
    }

    public void resumeGame() {
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
        mainMenuState = new MainMenuState();
        playingState = new PlayingState(keysPressed);
        setUpGameLoop();
        switchToMenu();
        setUpHashSet();
    }
}

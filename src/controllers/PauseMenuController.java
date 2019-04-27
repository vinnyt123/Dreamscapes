package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.GameManager;


public class PauseMenuController {

    @FXML private Button pauseButton;
    @FXML private AnchorPane pauseMenuBlock;
    @FXML private ProgressBar healthBar;
    @FXML private Label deathCount;
    @FXML private Label timeCount;

    public Label getDeathCount() {
        return deathCount;
    }

    public Label getTimeCount() {
        return timeCount;
    }

    public ProgressBar getHealthBar() {
        return healthBar;
    }

    @FXML
    private void pauseButtonClicked() {
        pauseGame();
    }

    @FXML
    private void exitButtonPressed() {
        pauseMenuBlock.setVisible(false);
        ((GameManager) pauseButton.getScene().getRoot()).switchToMenu();
    }

    @FXML
    private void resumeButtonPressed() {
        pauseMenuBlock.setVisible(false);
        ((GameManager) pauseButton.getScene().getRoot()).resumeGame();
    }

    public void pauseGame() {
        pauseMenuBlock.setVisible(true);
        ((GameManager) pauseButton.getScene().getRoot()).pauseGame();
    }
}

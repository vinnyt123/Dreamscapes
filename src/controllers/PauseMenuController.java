package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import model.GameManager;

public class PauseMenuController {

    @FXML private Button pauseButton;
    @FXML private AnchorPane pauseMenuBlock;

    @FXML
    private void pauseButtonPressed() {
        pauseMenuBlock.setVisible(true);
        ((GameManager) pauseButton.getScene().getRoot()).pauseGame();
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
}

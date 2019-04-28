package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.GameManager;
import model.Main;
import model.MainMenuState;

public class MainMenuController {

    @FXML private Button playBtn;
    @FXML private Button scoresButton;


    @FXML
    private void playBtnPressed() {
        ((GameManager) playBtn.getScene().getRoot()).switchToPlayingGame();
    }

    @FXML
    private void editControlsPressed() {
        ((GameManager) playBtn.getScene().getRoot()).switchToMenu();
    }

    @FXML
    private void onScoresButtonPressed() {
        GameManager gameManager = ((GameManager) playBtn.getScene().getRoot());
        gameManager.getMainMenuState().setScreen(MainMenuState.scoresId);
    }
}

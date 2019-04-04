package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.GameManager;

public class MainMenuController {

    @FXML private Button playBtn;

    @FXML
    private void playBtnPressed() {
        ((GameManager) playBtn.getScene().getRoot()).switchToPlayingGame();
    }
}

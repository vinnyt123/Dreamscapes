package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.GameManager;

public class GameCompleteController {

    @FXML private Label timeLabel;

    @FXML
    private void exitButtonClicked() {
        ((GameManager) timeLabel.getScene().getRoot()).switchToMenu();
    }

}

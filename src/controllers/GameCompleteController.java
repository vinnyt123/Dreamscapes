package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.GameManager;
import model.MainMenuState;

public class GameCompleteController {

    @FXML private Label timeLabel;
    @FXML private Label deathsLabel;


    @FXML
    private void exitButtonClicked() {
        ((GameManager) timeLabel.getScene().getRoot()).switchToMenu();
    }

    public void setLabels(String timeLabel, int deathCount) {
        this.timeLabel.setText(timeLabel);
        this.deathsLabel.setText(deathCount + "");
    }

}

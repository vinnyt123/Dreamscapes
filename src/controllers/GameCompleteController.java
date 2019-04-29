package controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.GameManager;

public class GameCompleteController {

    @FXML private Label timeLabel;
    @FXML private Label deathsLabel;
    @FXML private Button saveScoreButton;
    @FXML private TextField saveScoreTextField;
    @FXML private Label scoreSavedLabel;



    @FXML
    private void exitButtonClicked() {
        ((GameManager) timeLabel.getScene().getRoot()).switchToMenu();
    }

    public void setLabels(String timeLabel, int deathCount) {
        this.timeLabel.setText(timeLabel);
        this.deathsLabel.setText(deathCount + "");
    }

    @FXML
    private void saveScoreButtonClicked(Event e) {
        if(e instanceof KeyEvent) {
            if(((KeyEvent) e).getCode().toString().equals("ENTER")) {
                saveScoreButton.setDisable(true);
                saveScoreTextField.setDisable(true);
                scoreSavedLabel.setVisible(true);
                ((GameManager) timeLabel.getScene().getRoot()).saveScore(saveScoreTextField.getText());
            }
        } else {
            saveScoreButton.setDisable(true);
            saveScoreTextField.setDisable(true);
            scoreSavedLabel.setVisible(true);
            ((GameManager) timeLabel.getScene().getRoot()).saveScore(saveScoreTextField.getText());
        }
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> saveScoreTextField.requestFocus());
    }
}

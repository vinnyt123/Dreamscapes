package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import model.GameManager;
import model.Main;
import model.MainMenuState;

public class MainMenuController {

    @FXML private Button playBtn;
    @FXML private Button scoresButton;
    @FXML private Button controlsButton;

    public void initialize() {
        playBtn.requestFocus();
    }


    @FXML
    private void playBtnPressed(Event e) {
        if(e instanceof KeyEvent) {
            if((((KeyEvent) e).getCode().toString().equals("ENTER") || ((KeyEvent) e).getCode().toString().equals("P")) && playBtn.isFocused()) {
                ((GameManager) playBtn.getScene().getRoot()).switchToPlayingGame();
            }
        } else {
            ((GameManager) playBtn.getScene().getRoot()).switchToPlayingGame();
        }
    }

    @FXML
    private void editControlsPressed(Event e) {
        if(e instanceof KeyEvent) {
            if((((KeyEvent) e).getCode().toString().equals("ENTER") || ((KeyEvent) e).getCode().toString().equals("S")) && scoresButton.isFocused()) {
                ((GameManager) playBtn.getScene().getRoot()).getMainMenuState().setScreen(MainMenuState.controlsId);
            }
        } else {
            ((GameManager) playBtn.getScene().getRoot()).getMainMenuState().setScreen(MainMenuState.controlsId);
        }
    }

    @FXML
    private void onScoresButtonPressed(Event e) {
        if(e instanceof KeyEvent) {
            if((((KeyEvent) e).getCode().toString().equals("ENTER") || ((KeyEvent) e).getCode().toString().equals("C")) && controlsButton.isFocused()) {
                GameManager gameManager = ((GameManager) playBtn.getScene().getRoot());
                gameManager.getMainMenuState().setScreen(MainMenuState.scoresId);
            }
        } else {
            GameManager gameManager = ((GameManager) playBtn.getScene().getRoot());
            gameManager.getMainMenuState().setScreen(MainMenuState.scoresId);
        }
    }
}

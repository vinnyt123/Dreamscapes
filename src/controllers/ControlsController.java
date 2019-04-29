package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.GameManager;
import model.MainMenuState;

public class ControlsController {

    @FXML private Button backButton;

    @FXML
    private void backBtnPressed() {
        ((GameManager) backButton.getScene().getRoot()).getMainMenuState().setScreen(MainMenuState.mainMenuID);
    }
}

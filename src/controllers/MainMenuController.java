package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ControlledScreen;
import model.Main;
import model.GameManager;

public class MainMenuController implements ControlledScreen {

    private GameManager gameManager;


    @FXML
    private Button playBtn;

    @FXML
    private void playBtnPressed() {
        gameManager.startGame();
    }

    @Override
    public void setScreenParent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}

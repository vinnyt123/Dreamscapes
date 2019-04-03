package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ControlledScreen;
import model.GameManager;
import model.Main;
import model.ScreenManager;

public class MainMenuController implements ControlledScreen {

    private ScreenManager screenManager;


    @FXML
    private Button playBtn;

    @FXML
    private void playBtnPressed() {
        screenManager.setScreen(Main.map0ID);
        new GameManager(playBtn.getScene());
    }

    @Override
    public void setScreenParent(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

}

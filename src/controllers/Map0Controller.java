package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ControlledScreen;
import model.GameManager;
import model.Main;
import model.ScreenManager;

public class Map0Controller implements ControlledScreen {

    private ScreenManager screenManager;

    @Override
    public void setScreenParent(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}

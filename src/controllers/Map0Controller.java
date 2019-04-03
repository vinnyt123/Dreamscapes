package controllers;

import model.ControlledScreen;
import model.GameManager;

public class Map0Controller implements ControlledScreen {

    private GameManager gameManager;

    @Override
    public void setScreenParent(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}

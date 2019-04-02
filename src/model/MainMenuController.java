package model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML private Button playBtn;

    @FXML
    private void playBtnPressed() {
        System.out.println("Playing game!!!!!");
    }
}

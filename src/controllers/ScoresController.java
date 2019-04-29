package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GameManager;
import model.MainMenuState;
import model.Score;

import java.util.List;

public class ScoresController {

    @FXML
    TableView tableView;
    @FXML
    TableColumn<Score, String> nameColumn;
    @FXML
    TableColumn timeColumn;
    @FXML
    TableColumn deathsColumn;

    @FXML
    Button backButton2;

    public void setTableData(List<Score> highScores) {
        tableView.setItems(FXCollections.observableArrayList(highScores));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("playerName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("timeString"));
        deathsColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("deathsCount"));
    }

    @FXML
    public void initialize() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPlaceholder(new Label("No High Scores Saved"));
        //backButton2.requestFocus();
    }

    @FXML
    public void onBackPressed() {
        ((GameManager) tableView.getScene().getRoot()).getMainMenuState().setScreen(MainMenuState.mainMenuID);
    }

}

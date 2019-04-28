package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HighScores;
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

    public void setTableData(List<Score> highScores) {

        tableView.setItems(FXCollections.observableArrayList(highScores));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("playerName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("timeString"));
        deathsColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("deathsCount"));
    }

}

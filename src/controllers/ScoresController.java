package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HighScores;
import model.Score;

public class ScoresController {

    @FXML
    TableView tableView;
    @FXML
    TableColumn nameColumn;
    @FXML
    TableColumn timeColumn;
    @FXML
    TableColumn deathsColumn;

    public void setTableData(ObservableList<HighScores> highScores) {
        tableView.setItems(highScores);
        nameColumn.setCellFactory(new PropertyValueFactory<Score, String>("playerName"));
        timeColumn.setCellFactory(new PropertyValueFactory<Score, String>("timeString"));
        timeColumn.setCellFactory(new PropertyValueFactory<Score, String>("deathsCount"));
    }

}

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScores implements Serializable {
    private ObservableList<Score> highScores;

    public HighScores() {
        highScores = FXCollections.observableArrayList();
    }
    public void addScore(Score score) {
        highScores.add(score);
        Collections.sort(highScores);
    }

    public void printScores() {
        for (Score score : highScores) {
            System.out.println(score);
        }
    }

    public boolean isEmpty() {
        return highScores.isEmpty();
    }
}

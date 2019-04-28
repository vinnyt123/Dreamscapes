package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScores implements Serializable {
    public List<Score> getHighScores() {
        return highScores;
    }

    private List<Score> highScores;

    public HighScores() {
        highScores = new ArrayList<>();
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

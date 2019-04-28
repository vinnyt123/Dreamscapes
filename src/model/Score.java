package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private final SimpleStringProperty timeString;
    private final SimpleIntegerProperty deathsCount;
    private final long timeSeconds;
    private final SimpleStringProperty playerName;

    public Score(long timeSeconds, String timeString, int deathsCount, String playerName) {
        this.timeString = new SimpleStringProperty(timeString);
        this.timeSeconds = timeSeconds;
        this.deathsCount = new SimpleIntegerProperty(deathsCount);
        this.playerName = new SimpleStringProperty(playerName);
    }

    @Override
    public String toString() {
        return ("Name: " + playerName + " Time: " + timeString + " Deaths: " + deathsCount);
    }

    @Override
    public int compareTo(Score score) {
        if (timeSeconds > score.timeSeconds) {
            return 1;
        } else if (timeSeconds < score.timeSeconds){
            return -1;
        }
        return 0;
    }

    public SimpleStringProperty getTimeString() {
        return playerName;
    }

    public SimpleIntegerProperty getDeathsCount() {
         return deathsCount;
    }

    public SimpleStringProperty getPlayerName() {
        return playerName;
    }
}

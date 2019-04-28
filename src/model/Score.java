package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private final String timeString;
    private final String deathsCount;
    private final long timeSeconds;
    private final String playerName;

    public Score(long timeSeconds, String timeString, int deathsCount, String playerName) {
        this.timeString = timeString;
        this.timeSeconds = timeSeconds;
        this.deathsCount = deathsCount + "";
        this.playerName = playerName;
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

    /*public SimpleStringProperty getTimeString() {
        return playerName;
    }

    public SimpleIntegerProperty getDeathsCount() {
         return deathsCount;
    }

    public SimpleStringProperty getPlayerName() {
        return playerName;
    }*/

    public String getPlayerName() {
        return playerName;
    }

    public String getDeathsCount() {
        return deathsCount;
    }

    public String getTimeString() {
        return timeString;
    }
}

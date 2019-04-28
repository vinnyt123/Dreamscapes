package model;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private String timeString;
    private int deathsCount;
    private long timeSeconds;
    private String playerName;

    public Score(long timeSeconds, String timeString, int deathsCount, String playerName) {
        this.timeString = timeString;
        this.timeSeconds = timeSeconds;
        this.deathsCount = deathsCount;
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
}

package model;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
import java.net.URISyntaxException;

public class SoundEffect {

    private MediaPlayer mp;

    SoundEffect(String fileLocation) {
        Media sound = new Media(getClass().getClassLoader()
                .getResource(fileLocation).toString());
        mp = new MediaPlayer(sound);
        mp.setOnEndOfMedia(() -> mp.stop());
        mp.setVolume(0.50);
    }

    void setVolume(double volume) {
        mp.setVolume(volume);
    }

    void playSound() {
        if(!(mp.getStatus() == MediaPlayer.Status.PLAYING)) {
            mp.play();
        }
    }
}

package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class SoundEffect {

    private MediaPlayer mp;

    SoundEffect(String fileLocation) {
        try {
            Media sound = new Media(SoundEffect.class.getResource(fileLocation).toURI().toString());
            mp = new MediaPlayer(sound);
            mp.setOnEndOfMedia(() -> mp.stop());
            mp.setVolume(0.25);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void playSound() {
        if(!(mp.getStatus() == MediaPlayer.Status.PLAYING)) {
            mp.play();
        }
    }
}

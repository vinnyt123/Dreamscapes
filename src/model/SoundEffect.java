package model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class SoundEffect {

    private Clip clip;

    SoundEffect(String fileLocation) {
        try {
            URL url = getClass().getClassLoader().getResource(fileLocation);
            assert url != null;
            InputStream input = url.openStream();
            InputStream audio = new BufferedInputStream(input);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audio);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    void playSound() {
        if(!clip.isRunning()) {
            clip.loop(1);
        }
    }

}

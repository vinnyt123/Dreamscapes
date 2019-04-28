package model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class HealthPotion extends Item {

    private static final Image TILE_IMAGE = new Image("images/hp_potion.png");
    private SoundEffect itemSound = new SoundEffect("resources/sounds/item.wav");
    Timeline healthRestoreTransition;

    public HealthPotion(Node node) {
        super(TILE_IMAGE, node);
        healthRestoreTransition = new Timeline();
    }

    @Override
    public boolean intersect(Player player) {
        if (player.getBounds().intersects(this.getBoundsInParent()) && player.getHealth().get() != 1.0) {
            itemSound.playSound();
            healthRestoreTransition.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                    new KeyValue(player.getHealth(), 1.0)));
            healthRestoreTransition.setCycleCount(1);
            healthRestoreTransition.play();
            return true;
        } else {
            return false;
        }
    }
}

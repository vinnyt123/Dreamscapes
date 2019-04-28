package model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class HealthPotion extends Item {

    private static final Image TILE_IMAGE = new Image("images/hp_potion.png");
    Timeline healthRestoreTranstion;

    public HealthPotion(Node node) {
        super(TILE_IMAGE, node);
        healthRestoreTranstion = new Timeline();
    }

    @Override
    public boolean intersect(Player player) {
        if (player.getBounds().intersects(this.getBoundsInParent()) && player.getHealth().get() != 1.0) {
            healthRestoreTranstion.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                    new KeyValue(player.getHealth(), 1.0)));
            healthRestoreTranstion.setCycleCount(1);
            healthRestoreTranstion.play();
            return true;
        } else {
            return false;
        }
    }
}

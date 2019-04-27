package model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class DoubleJumpBoots extends Item  {

    private static final Image TILE_IMAGE = new Image("images/bootsTile.png");

    public DoubleJumpBoots(Node node) {
        super(TILE_IMAGE, node);

    }

    @Override
    public boolean intersect(Player player) {
        if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            player.addDoubleJumpBoots();
            ((GameManager) this.getScene().getRoot()).getPlayingState().createBootsOverLay();
            return true;
        }
        return false;
    }
}

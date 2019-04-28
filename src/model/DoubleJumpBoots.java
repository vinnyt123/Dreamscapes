package model;

import javafx.scene.Node;
import javafx.scene.image.Image;


public class DoubleJumpBoots extends Item  {

    private static final Image TILE_IMAGE = new Image("images/bootsTile.png");
    private SoundEffect itemSound = new SoundEffect("resources/sounds/item.wav");

    public DoubleJumpBoots(Node node) {
        super(TILE_IMAGE, node);

    }

    @Override
    public boolean intersect(Player player) {
        if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            itemSound.playSound();
            player.addDoubleJumpBoots();
            ((GameManager) this.getScene().getRoot()).getPlayingState().createBootsOverLay();
            return true;
        }
        return false;
    }
}

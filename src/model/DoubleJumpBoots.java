package model;

import javafx.scene.Node;
import javafx.scene.image.Image;


public class DoubleJumpBoots extends Item  {

    private static final Image TILE_IMAGE = new Image("images/bootsTile.png");

    public DoubleJumpBoots(Node node) {
        super(TILE_IMAGE, node);

    }

    @Override
    public boolean intersect(Player player) {
        if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            player.addDoubleJumpBoots();
            return true;
        }
        return false;
    }
}

package model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class WeaponFists extends Weapon {

    private Player player;
    private static final double RANGE_ABOVE = 14;
    private static final double RANGE_WIDTH = 50;
    private static final double RANGE_HEIGHT = 70;
    private static final double DAMAGE = 3;
    private Bounds bounds;

    public WeaponFists(Player player) {
        super();
        this.player = player;
        damage = DAMAGE;
    }

    public Bounds getAttackBounds(boolean isRight) {
        if(isRight) {
            bounds = new BoundingBox(player.getBounds().getMaxX(), player.getBounds().getMinY() - RANGE_ABOVE, RANGE_WIDTH, RANGE_HEIGHT);
        } else {
            bounds = new BoundingBox(player.getBounds().getMinX() - RANGE_WIDTH, player.getBounds().getMinY() - RANGE_ABOVE, RANGE_WIDTH, RANGE_HEIGHT);
        }
        return bounds;
    }
}

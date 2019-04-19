package model;

import javafx.geometry.Bounds;
import javafx.scene.Group;

public abstract class Weapon extends Group {

    double damage;

    public abstract Bounds getAttackBounds(boolean isRight);

    public double getDamage() {
        return damage;
    }

}

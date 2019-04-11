package model;

import javafx.scene.Group;

public abstract class Weapon extends Group {

    double range;
    double damage;

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

}

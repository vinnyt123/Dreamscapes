package model;

import javafx.geometry.Bounds;
import javafx.scene.Group;

public abstract class PlayerSprite extends Group {

    public abstract void walkLeft();

    public abstract void walkRight();

    public abstract void jumpLeft();

    public abstract void jumpRight();

    public abstract void attackLeft();

    public abstract void attackRight();

    public abstract void standRight();

    public abstract void standLeft();

    public abstract void damageRight();

    public abstract void damageLeft();

    public abstract Bounds getBounds();
}

package model;

import javafx.animation.RotateTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class PlayerSprite extends Group {

    private RotateTransition rotateTransition;
    ColorAdjust colorAdjust;
    ImageView imageView;

    public PlayerSprite() {
        rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(500));
        rotateTransition.setNode(this);

        colorAdjust = new ColorAdjust();
    }

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

    public void flipLeft() {
        rotateTransition.setByAngle(-360);
        rotateTransition.play();
}

    public void flipRight() {
        rotateTransition.setByAngle(360);
        rotateTransition.play();
    }

    public abstract void addBoots();

    public abstract void removeBoots();
}

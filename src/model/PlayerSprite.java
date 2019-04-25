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

    boolean attacking = false;

    boolean damaged = false;
    SpriteAnimation currentAnimation;

    SpriteAnimation walkRight;

    SpriteAnimation walkLeft;

    SpriteAnimation jumpRight;
    SpriteAnimation jumpLeft;
    SpriteAnimation damageRight;
    SpriteAnimation damageLeft;
    SpriteAnimation standRight;
    SpriteAnimation standLeft;
    SpriteAnimation attackRight;
    SpriteAnimation attackLeft;
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

    void redFlashOn() {
        colorAdjust.setContrast(1);
        //colorAdjust.setHue(-0.2);
        //colorAdjust.setBrightness(-0.1);
        //colorAdjust.setSaturation(0.3);
    }

    void redFlashOff() {
        colorAdjust.setContrast(0);
        //colorAdjust.setHue(0);
        //colorAdjust.setBrightness(0);
        //colorAdjust.setSaturation(0);
    }

    SpriteAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    void stopAll() {
        currentAnimation.stop();
        walkRight.stop();
        walkLeft.stop();
        jumpRight.stop();
        jumpLeft.stop();
        damageRight.stop();
        damageLeft.stop();
        standRight.stop();
        standLeft.stop();
        attackRight.stop();
        attackLeft.stop();
    }

    boolean isAttacking() {
        return attacking;
    }

    boolean isDamaged() {
        return damaged;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
}

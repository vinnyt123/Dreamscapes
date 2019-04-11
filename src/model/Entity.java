package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity extends Group {

    Point2D velocity = new Point2D(0,0);
    ColorAdjust colorAdjust = new ColorAdjust();
    Timer timer = new Timer();
    boolean isFlashing = false;
    double health;
    boolean isKnockback = false;
    private Point2D lastMove;
    private boolean inAir = true;
    private boolean isRight = true;

    public abstract void move();

    public void setRight(boolean right) {
        isRight = right;
    }

    public void applyGravity() {
        if(velocity.getY() < Map.TERMINAL_VELOCITY) {
            velocity = velocity.add(0, Map.GRAVITY);
        }
    }

    public boolean isFlashing() {
        return isFlashing;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public boolean getInAir() {
        return this.inAir;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void applyVelocity() {
        setTranslateX(getTranslateX() + velocity.getX());
        setTranslateY(getTranslateY() + velocity.getY());
        lastMove = velocity;
        if(!isKnockback) {
            setVelocity(new Point2D(0, velocity.getY()));
        }
    }

    public Point2D getLastMove() {
        return lastMove;
    }

    public void undoMove() {
        setTranslateX(getTranslateX() - lastMove.getX());
        setTranslateY(getTranslateY() - lastMove.getY());
    }

    class coolDownTimer extends TimerTask {
        public void run() {
            colorAdjust.setSaturation(0);
            isFlashing = false;
        }
    }
}

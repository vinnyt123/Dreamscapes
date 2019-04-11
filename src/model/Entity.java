package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.List;

public abstract class Entity extends Group {

    Point2D velocity = new Point2D(0,0);
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
        if(velocity.getY() < IsGravityEffected.TERMINAL_VELOCITY) {
            velocity = velocity.add(0, IsGravityEffected.GRAVITY);
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
}

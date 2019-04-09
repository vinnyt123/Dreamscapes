package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.List;

public abstract class Entity extends Group {

    protected Point2D velocity = new Point2D(0,0);
    private Point2D lastMove;

    public void applyGravity() {
        if(velocity.getY() < IsGravityEffected.TERMINAL_VELOCITY) {
            velocity = velocity.add(0, IsGravityEffected.GRAVITY);
        }
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
        System.out.println(velocity.getX() + "  " +  velocity.getY());
        lastMove = velocity;
        setVelocity(new Point2D(0, velocity.getY()));

    }

    public Point2D getLastMove() {
        return lastMove;
    }

    public void setLastMove(Point2D point2D) {
        lastMove = point2D;
    }

    public abstract void move();

    public void undoMove() {
        setTranslateX(getTranslateX() - lastMove.getX());
        setTranslateY(getTranslateY() - lastMove.getY());
    }
}

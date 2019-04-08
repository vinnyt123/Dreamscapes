package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.List;

public abstract class Entity extends Group {

    protected Point2D velocity = new Point2D(0,0);

    public void applyGravity() {
        if(velocity.getY() < IsGravityEffected.TERMINAL_VELOCITY && this instanceof IsGravityEffected) {
            velocity = velocity.add(0, IsGravityEffected.GRAVITY);
        }
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    protected void translateThis() {
        setTranslateX(getTranslateX() + velocity.getX());
        setTranslateY(getTranslateY() + velocity.getY());
    }

    public abstract void move();
}

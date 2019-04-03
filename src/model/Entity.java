package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public abstract class Entity extends Group {

    protected Point2D velocity = new Point2D(0,0);

    public void applyGravity() {
        velocity = velocity.add(0d,0.1d);
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void move() {
        applyGravity();
        this.setTranslateX(getTranslateX() + velocity.getX());
        this.setTranslateY(getTranslateY() + velocity.getY());
    }
}

package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public abstract class Entity extends Group {

    protected Point2D velocity = new Point2D(0,0);
    private static final double GRAVITY = 0.2;

    public void applyGravity() {
        velocity = velocity.add(0, GRAVITY);
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void move() {
        applyGravity();
        this.setTranslateX(getTranslateX() + velocity.getX());
        this.setTranslateY(getTranslateY() + velocity.getY());
        //Reset horizontal velocity
        velocity = new Point2D(0, velocity.getY());
    }
}

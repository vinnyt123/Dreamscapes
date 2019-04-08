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

    public void move(List<Rectangle> walls) {
        applyGravity();

        moveX(walls, velocity.getX());
        moveY(walls, velocity.getY());
    }

    //TODO: Fix corner bug when you jump without moving left or right
    //Move x & y methods automatically do collisions: only move in increments of 1 until hit a wall then stop
    private void moveX(List<Rectangle> walls, double xDistance) {
        boolean movingRight = xDistance > 0;
        for(int i = 0; i < Math.abs(xDistance); i++) {
            for(Rectangle wall : walls ) {
                if(getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if(movingRight) {
                        if(getTranslateX() + this.getBoundsInParent().getWidth() == wall.getBoundsInParent().getMinX()) {
                            return;
                        }
                    } else {
                        if(getTranslateX() == wall.getBoundsInParent().getMaxX()) {
                            return;
                        }
                    }
                }
            }
            setTranslateX(getTranslateX() + (movingRight ? 1 : -1));
        }
        velocity = new Point2D(0, velocity.getY());
    }

    private void moveY(List<Rectangle> walls, double yDistance) {
        boolean movingDown = yDistance > 0;
        for(int i = 0; i < Math.abs(yDistance); i++) {
            for(Rectangle wall : walls) {
                if(getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if(movingDown) {
                        if(getTranslateY() + this.getBoundsInParent().getHeight() == wall.getBoundsInParent().getMinY()) {
                            if(this instanceof Player) {
                                ((Player) this).setInAir(false);
                            }
                            velocity = new Point2D(velocity.getX(), 0);
                            return;
                        }
                    } else {
                        if(getTranslateY() == wall.getBoundsInParent().getMaxY()) {
                            velocity = new Point2D(velocity.getX(), 0);
                            return;
                        }
                    }
                } else {
                    //Player fell of platform without jumping
                    if(this instanceof Player) {
                        ((Player) this).setInAir(true);
                    }
                }
            }
            setTranslateY(getTranslateY() + (movingDown ? 1 : -1));
        }
    }
}

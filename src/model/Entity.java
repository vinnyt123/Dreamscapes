package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.List;

public abstract class Entity extends Group {

    protected Point2D velocity = new Point2D(0,0);
    private static final double GRAVITY = 0.4;
    private static final double TERMINAL_VELOCITY = 15;

    public void applyGravity() {
        if(velocity.getY() < TERMINAL_VELOCITY) {
            velocity = velocity.add(0, GRAVITY);
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

        //System.out.println("velocity x: " + velocity.getX() + " velocity y: " + velocity.getY());
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
                        if(getTranslateX() + Player.WIDTH == wall.getBoundsInParent().getMinX()) {
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
                        if(getTranslateY() + Player.HEIGHT == wall.getBoundsInParent().getMinY()) {
                            Player player = (Player) this;
                            player.setInAir(false);
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
                    Player player = (Player) this;
                    player.setInAir(true);
                }
            }
            setTranslateY(getTranslateY() + (movingDown ? 1 : -1));
        }
    }
}

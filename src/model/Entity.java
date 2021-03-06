package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity extends Group {

    static final long DYING_TIME = 2500;
    Point2D velocity = new Point2D(0,0);
    Timer timer = new Timer();
    DoubleProperty health = new SimpleDoubleProperty();
    boolean isFlashing = false;
    boolean isKnockback = false;
    private Point2D lastMove = new Point2D(0,0);
    boolean inAir = true;
    boolean isRight = true;
    boolean isDead = false;
    boolean isDying = false;
    boolean hasDoubleJumped = false;
    double dt;

    public abstract void move();
    public abstract Bounds getBounds();

    public void setRight(boolean right) {
        isRight = right;
    }

    public void applyGravity() {
        if(velocity.getY() < Map.TERMINAL_VELOCITY) {
            velocity = velocity.add(0, Map.GRAVITY);
        }
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
        setTranslateX((getTranslateX() + velocity.getX()));
        setTranslateY((getTranslateY() + velocity.getY()));
        lastMove = velocity;
    }

    public Point2D getLastMove() {
        return lastMove;
    }

    public void undoMove() {
        setTranslateX(getTranslateX() - lastMove.getX());
        setTranslateY(getTranslateY() - lastMove.getY());
    }

    public void setDoubleJumped(boolean doubleJumped) {
        this.hasDoubleJumped = doubleJumped;
    }

    class coolDownTimer extends TimerTask {
        public void run() {
            isFlashing = false;
        }
    }

    class dyingTimer extends TimerTask {
        public void run() {
            isDead = true;
        }
    }

    public void spawnAt(Point2D spawn) {
        this.setTranslateX(spawn.getX());
        this.setTranslateY(spawn.getY());
    }

    /*public double distanceTo(Entity entity) {
        Point2D thisPosition = new Point2D(getTranslateX(), getTranslateY());
        Point2D entityPosition = new Point2D(entity.getTranslateX(), entity.getTranslateY());
        return thisPosition.distance(entityPosition);
    }*/

    public DoubleProperty getHealth() {
        return health;
    }
}

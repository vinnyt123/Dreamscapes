package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;

public class Player extends Entity {

    private static final double JUMPHEIGHT = -12;
    static final double WIDTH = 20;
    static final double HEIGHT = 30;
    static final double RUNSPEED = 5;
    private boolean inAir = true;
    Controls controls = new Controls();

    public Player() {
        super();
        createSprite();
    }

    private void createSprite() {
        Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(100);
        setTranslateY(600);
        this.getChildren().addAll(rectangle);
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void crouch() {

    }

    public void jump() {
        if(!inAir) {
            inAir = true;
            setVelocity(new Point2D(0, JUMPHEIGHT));
        }
    }

    public void moveLeft() {
        setVelocity(new Point2D(-RUNSPEED, getVelocity().getY()));
    }

    public void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
    }

    public void attack() {


    }

    public void switchWeapon() {

    }


    public void performActions(HashSet<String> keysPressed) {
        if (keysPressed.contains(controls.getCrouchKey())) {
            crouch();
        }

        if (keysPressed.contains(controls.getJumpKey())) {
            jump();
        }

        if (keysPressed.contains(controls.getLeftKey())) {
            moveLeft();
        }

        if (keysPressed.contains(controls.getRightKey())) {
            moveRight();
        }

        if (keysPressed.contains(controls.getSwitchKey())) {
            switchWeapon();
        }

        if (keysPressed.contains(controls.getAttackKey())) {
            attack();
        }
    }
}

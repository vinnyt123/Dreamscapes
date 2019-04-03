package model;


import com.sun.org.apache.regexp.internal.RECompiler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;

public class Player extends Entity {

    Controls controls = new Controls();

    public Player() {
        super();
        createSprite();
    }

    private void createSprite() {
        Rectangle rectangle = new Rectangle(200,200, 20, 30);
        this.getChildren().addAll(rectangle);
    }

    public void crouch() {

    }

    public void jump() {


    }

    public void moveLeft() {

    }

    public void moveRight() {

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

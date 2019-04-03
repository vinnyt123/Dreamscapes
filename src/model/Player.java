package model;


import javafx.scene.Group;

import java.util.HashSet;

public class Player extends Group {

    Controls controls = new Controls();

    public Player() {
        super();
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

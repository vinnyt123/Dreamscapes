package model;

import java.util.HashSet;

public class Controls {

    private String jumpKey;
    private String crouchKey;
    private String leftKey;
    private String rightKey;
    private String attackKey;
    private String switchKey;




    public Controls() {
        jumpKey = "UP";
        crouchKey = "DOWN";
        leftKey = "LEFT";
        rightKey = "RIGHT";
        attackKey = "SPACE";
        switchKey = "F";
    }

    boolean isAnyKeyPressed(HashSet<String> keysPressed) {
        return keysPressed.contains(jumpKey) || keysPressed.contains(leftKey) || keysPressed.contains(rightKey) || keysPressed.contains(attackKey);
    }

    public String getCrouchKey() {
        return crouchKey;
    }

    public String getJumpKey() {
        return jumpKey;
    }

    public String getLeftKey() {
        return leftKey;
    }

    public String getRightKey() {
        return rightKey;
    }

    public String getSwitchKey() {
        return switchKey;
    }

    public String getAttackKey() {
        return attackKey;
    }
}

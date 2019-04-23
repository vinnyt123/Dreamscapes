package model;

import controllers.PauseMenuController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import java.util.*;

public class Player extends Entity {

    private static final double JUMPHEIGHT = -12;
    private static final long DAMAGE_COOLDOWN = 800;
    private static final double RUNSPEED = 5;
    private static final double ATTACKSLIDE = 5;
    static final double WIDTH = 35;
    static final double HEIGHT = 50;
    IntegerProperty deathCount = new SimpleIntegerProperty();
    private PlayerSprite playerSprite = new DefaultPlayer();

    private int attackCount = 0;
    private int jumpCount = 0;
    private List<Weapon> playerWeapons = new ArrayList<>();
    private Weapon currentWeapon;
    private Controls controls = new Controls();
    boolean isAttacking = false;
    private HashSet<String> keysPressed;
    private boolean hasBoots = false;
    public Player(HashSet<String> keysPressed) {
        super();
        this.keysPressed = keysPressed;
        deathCount.setValue(0);
        health.setValue(1);
        currentWeapon = new WeaponFists(this);
        playerWeapons.add(currentWeapon);
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    //Cool effect for if the player is standing in water or something (creates reflection)
    //Reflection reflection = new Reflection();

    //imageView.setEffect(reflection);
    public void createSprite() {
        GameManager gm = (GameManager) getScene().getRoot();
        PauseMenuController pm = gm.getPlayingState().getPauseLoader().getController();
        pm.getHealthBar().progressProperty().bind(health);
        pm.getDeathCount().textProperty().bind(deathCount.asString());

        this.getChildren().addAll(playerSprite);
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void crouch() {

    }

    public void jump() {
        if(!this.getInAir()) {
            this.setInAir(true);
            setVelocity(new Point2D(0, JUMPHEIGHT));
        } else if (hasBoots && !hasDoubleJumped && jumpCount == 1) {
            hasDoubleJumped = true;
            if (isRight) {
                playerSprite.flipRight();
            } else {
                playerSprite.flipLeft();
            }
            setVelocity(new Point2D(0, JUMPHEIGHT));
        }
    }

    public void moveLeft() {
        setVelocity(new Point2D(-RUNSPEED, getVelocity().getY()));
        this.setRight(false);
    }

    public void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
        this.setRight(true);
    }

    public void attack() {
        if(attackCount == 1) {
            isAttacking = true;
            velocity = new Point2D((isRight) ? ATTACKSLIDE : -ATTACKSLIDE, velocity.getY());
        }
    }

    public void switchWeapon() {

    }

    public void knockBack(double xDistance, double yDistance, boolean isDamage) {
        isKnockback = true;
        setVelocity(new Point2D(xDistance, yDistance));
        if (isDamage) {
            colorAdjust.setSaturation(1);
            isFlashing = true;
            timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
        }
    }

    public boolean isFlashing() {
        return isFlashing;
    }

    @Override
    public void move() {
        if(isKnockback) {
            if(Math.round(velocity.getX()) == 0) {
                isKnockback = false;
            } else {
                setVelocity(new Point2D((velocity.getX() > 0) ? velocity.getX() - 1: velocity.getX() + 1, (velocity.getY() > 0) ? velocity.getY() - 1: velocity.getY() + 1));
            }
            applyGravity();
            applyVelocity();
            return;
        }

        if (keysPressed.contains(controls.getCrouchKey())) {
            crouch();
        }

        if (keysPressed.contains(controls.getJumpKey())) {
            jumpCount++;
            jump();
        } else {
            jumpCount = 0;
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
            attackCount++;
            attack();
        } else {
            attackCount = 0;
        }

        applyGravity();
        applyVelocity();
        playAnimation();
    }

    @Override
    public Bounds getBounds() {
        return new BoundingBox(playerSprite.getBounds().getMinX() + getTranslateX(),
                playerSprite.getBounds().getMinY() + getTranslateY(), playerSprite.getBounds().getWidth(), playerSprite.getBounds().getHeight());
    }

    private void playAnimation() {
        if(isFlashing && isRight) {
            playerSprite.damageRight();
        } else if(isFlashing) {
            playerSprite.damageLeft();
        } else if (isAttacking && isRight) {
            playerSprite.attackRight();
        } else if (isAttacking) {
            playerSprite.attackLeft();
        } else if(keysPressed.isEmpty() && !inAir && isRight) {
            playerSprite.standRight();
        } else if(keysPressed.isEmpty() && !inAir) {
            playerSprite.standLeft();
        } else if(inAir && isRight) {
            playerSprite.jumpRight();
        } else if(inAir) {
            playerSprite.jumpLeft();
        } else if(keysPressed.contains(controls.getRightKey())) {
            playerSprite.walkRight();
        } else if(!inAir && keysPressed.contains(controls.getLeftKey())) {
            playerSprite.walkLeft();
        }
    }



    @Override
    public void applyVelocity() {
        super.applyVelocity();
        if(!isKnockback) {
            setVelocity(new Point2D(0, velocity.getY()));
        }
    }

    public void addDoubleJumpBoots() {
        playerSprite.addBoots();
        hasBoots = true;
    }
}

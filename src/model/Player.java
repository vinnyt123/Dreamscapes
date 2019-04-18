package model;

import controllers.PauseMenuController;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.*;

public class Player extends Entity {

    private static final Image SPRITE_SHEET = new Image("images/sprite_sheet.png");
    private static final double JUMPHEIGHT = -12;
    private static final long DAMAGE_COOLDOWN = 800;
    private static final double RUNSPEED = 5;
    static final double WIDTH = 35;
    static final double HEIGHT = 50;
    private SpriteAnimation walkRight;
    private SpriteAnimation walkLeft;
    private SpriteAnimation jumpRight;
    private SpriteAnimation jumpLeft;
    private SpriteAnimation damageRight;
    private SpriteAnimation damageLeft;
    private SpriteAnimation standRight;
    private SpriteAnimation standLeft;
    private SpriteAnimation animation;

    private List<Weapon> playerWeapons = new ArrayList<>();
    private Weapon currentWeapon;
    private Controls controls = new Controls();
    private boolean hasDoubleJumped = false;
    boolean isAttacking = false;
    private HashSet<String> keysPressed;

    public Player(HashSet<String> keysPressed) {
        super();
        this.keysPressed = keysPressed;
        health.setValue(1);
        currentWeapon = new WeaponFists(this);
        playerWeapons.add(currentWeapon);
        //Spawn coords in map
        setTranslateX(100);
        setTranslateY(1650);
    }

    //Cool effect for if the player is standing in water or something (creates reflection)
    //Reflection reflection = new Reflection();
    //imageView.setEffect(reflection);
    public void createSprite() {
        GameManager gm = (GameManager) getScene().getRoot();
        PauseMenuController pm = gm.getPlayingState().getLoader().getController();
        pm.getHealthBar().progressProperty().bind(health);

        ImageView imageView = new ImageView(SPRITE_SHEET);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 97));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        //imageView.getTransforms().addAll(new Scale(-1, 1), new Translate(-WIDTH, 0));
        walkRight = new SpriteAnimation(imageView, Duration.millis(100), 11, 11, 72, 97, 0);
        walkLeft = new SpriteAnimation(imageView, Duration.millis(100), 11, 11, 72, 97, 97);
        jumpRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 194);
        jumpLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 291);
        damageRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 388);
        damageLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 485);
        standRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 582);
        standLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 679);
        animation = jumpRight;
        animation.getImageView().setEffect(colorAdjust);
        animation.setCycleCount(1);
        this.getChildren().addAll(imageView);
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
        isAttacking = true;
    }

    public void switchWeapon() {

    }

    public void knockBack(double xDistance, double yDistance) {
        isKnockback = true;
        setVelocity(new Point2D(xDistance, yDistance));
        colorAdjust.setSaturation(1);
        isFlashing = true;
        timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
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
                //TODO: fix bug where y doesn't matter as it's set back to 0 in wall move
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

        applyGravity();
        applyVelocity();
        playAnimation();
    }

    private void playAnimation() {
        if(isFlashing && isRight) {
            animation = damageRight;
        } else if(isFlashing) {
            animation = damageLeft;
        } else if(keysPressed.isEmpty() && !inAir && isRight) {
            animation = standRight;
        } else if(keysPressed.isEmpty() && !inAir) {
            animation = standLeft;
        } else if(inAir && isRight) {
            animation = jumpRight;
        } else if(inAir) {
            animation = jumpLeft;
        } else if(keysPressed.contains(controls.getRightKey())) {
            animation = walkRight;
        } else if(!inAir && keysPressed.contains(controls.getLeftKey())) {
            animation = walkLeft;
        }

        animation.play();
    }
}

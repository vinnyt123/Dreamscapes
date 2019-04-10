package model;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player extends Entity implements IsGravityEffected {

    private static final Image WALK_RIGHT_IMAGE = new Image("images/p1_walkR.png");
    private static final double JUMPHEIGHT = -12;
    private List<Weapon> playerWeapons = new ArrayList<>();
    private Weapon currentWeapon;
    static final double WIDTH = 35;
    static final double HEIGHT = 50;
    static final double RUNSPEED = 5;
    private Controls controls = new Controls();
    private Animation animationWalkRight;
    private boolean hasDoubleJumped = false;
    private HashSet<String> keysPressed;

    public Player(HashSet<String> keysPressed) {
        super();
        this.keysPressed = keysPressed;
        health = 50;
        currentWeapon = new WeaponFists(this);
        playerWeapons.add(currentWeapon);
        //Spawn coords in map
        setTranslateX(100);
        setTranslateY(1650);

        createSprite();
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    //Cool effect for if the player is standing in water or something (creates reflection)
    //Reflection reflection = new Reflection();
    //imageView.setEffect(reflection);
    private void createSprite() {
        //Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
        final ImageView imageViewR = new ImageView(WALK_RIGHT_IMAGE);
        imageViewR.setViewport(new Rectangle2D(0, 0, 72, 97));
        imageViewR.setFitWidth(WIDTH);
        imageViewR.setFitHeight(HEIGHT);
        //imageView.getTransforms().addAll(new Scale(-1, 1), new Translate(-WIDTH, 0));
        animationWalkRight = new SpriteAnimation(imageViewR, Duration.millis(100), 11, 11, 0, 0, 72, 97);
        animationWalkRight.setCycleCount(1);
        this.getChildren().addAll(imageViewR);
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
        if(!this.getInAir()) {
            animationWalkRight.play();
        }
    }

    public void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
        this.setRight(true);
        if(!this.getInAir()) {
            animationWalkRight.play();
        }
    }

    public void attack() {

    }

    public void switchWeapon() {

    }

    public void knockBack(boolean isRight, double distance) {
        isKnockback = true;
        if(isRight) {
            setVelocity(new Point2D(distance, - distance + 4));
        } else {
            setVelocity(new Point2D(-distance, - distance + 4));
        }
    }

    @Override
    public void move() {

        if(isKnockback) {
            if(velocity.getX() == 0) {
                isKnockback = false;
            } else {
                setVelocity(new Point2D((velocity.getX() > 0) ? velocity.getX() - 1: velocity.getX() + 1, velocity.getY()));
                setVelocity(new Point2D(velocity.getX(), (velocity.getX() < 0) ? velocity.getX()+ IsGravityEffected.GRAVITY : -velocity.getX() + IsGravityEffected.GRAVITY));

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
    }
}

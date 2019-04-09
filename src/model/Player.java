package model;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashSet;

public class Player extends Entity implements IsGravityEffected {

    private static final Image WALK_RIGHT_IMAGE = new Image("images/p1_walkR.png");
    private static final double JUMPHEIGHT = -12;
    static final double WIDTH = 35;
    static final double HEIGHT = 50;
    static final double RUNSPEED = 5;
    private boolean inAir = true;
    private boolean isRight = true;
    private Controls controls = new Controls();
    private Animation animationWalkRight;
    private boolean hasDoubleJumped = false;
    private HashSet<String> keysPressed;

    public Player(HashSet<String> keysPressed) {
        super();
        this.keysPressed = keysPressed;
        //Spawn coords in map
        setTranslateX(100);
        setTranslateY(1650);

        createSprite();
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
        isRight = false;
        if(!inAir) {
            animationWalkRight.play();
        }
    }

    public void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
        isRight = true;
        if(!inAir) {
            animationWalkRight.play();
        }
    }

    public void attack() {

    }

    public void switchWeapon() {

    }

    @Override
    public void move() {
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

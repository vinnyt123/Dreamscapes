package model;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashSet;

public class Player extends Entity {

    private static final Image IMAGE = new Image("images/p1_walk.png");
    private static final double JUMPHEIGHT = -12;
    static final double WIDTH = 35;
    static final double HEIGHT = 50;
    static final double RUNSPEED = 5;
    private boolean inAir = true;
    Controls controls = new Controls();
    Animation animation;

    public Player() {
        super();
        createSprite();
    }

    private void createSprite() {
        //Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
        //Spawn coords in map
        setTranslateX(100);
        setTranslateY(1650);
        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 97));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        animation = new SpriteAnimation(
                imageView,
                Duration.millis(100),
                11, 11,
                0, 0,
                72, 97
        );
        animation.setCycleCount(1);
        this.getChildren().addAll(imageView);
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
        if(!inAir) {
            animation.play();
        }
    }

    public void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
        if(!inAir) {
            animation.play();
        }
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

package model;

import controllers.PauseMenuController;
import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.*;

public class Player extends Entity {

    private static final Image SPRITE_SHEET = new Image("images/sprite_sheet.png");
    private ImageView imageViewAttack;
    private static final double JUMPHEIGHT = -12;
    private static final long DAMAGE_COOLDOWN = 800;
    private static final double RUNSPEED = 5;
    private static final double ATTACKSLIDE = 5;
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
    private SpriteAnimation attackRight;
    private SpriteAnimation attackLeft;
    private SpriteAnimation attackSwipeRight;
    private SpriteAnimation attackSwipeLeft;
    private SpriteAnimation animation;

    private int attackCount = 0;
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

        imageView = new ImageView(SPRITE_SHEET);
        imageViewAttack = new ImageView(SPRITE_SHEET);
        imageViewAttack.setViewport(new Rectangle2D(0, 0, 32, 32));
        imageViewAttack.setFitWidth(32);
        imageViewAttack.setFitHeight(32);
        imageViewAttack.setLayoutY(getLayoutY() + HEIGHT/4);
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
        attackRight = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 97, 776);
        attackLeft = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 97, 873);
        attackSwipeRight = new SpriteAnimation(imageViewAttack, Duration.millis(200), 4, 4, 32, 32, 970);
        attackSwipeLeft = new SpriteAnimation(imageViewAttack, Duration.millis(200), 4, 4, 32, 32, 1002);
        attackSwipeRight.setOnFinished(e -> this.getChildren().remove(imageViewAttack));
        attackSwipeLeft.setOnFinished(e -> this.getChildren().remove(imageViewAttack));
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
        if(attackCount == 1) {
            isAttacking = true;
            velocity = new Point2D((isRight) ? ATTACKSLIDE : -ATTACKSLIDE, velocity.getY());
            if(isRight && !(attackSwipeRight.getStatus() == Animation.Status.RUNNING) && !(attackSwipeLeft.getStatus() == Animation.Status.RUNNING)) {
                imageViewAttack.setLayoutX(getLayoutX() + WIDTH/2);
                this.getChildren().add(imageViewAttack);
                attackSwipeRight.play();
            } else if(!(attackSwipeRight.getStatus() == Animation.Status.RUNNING) && !(attackSwipeLeft.getStatus() == Animation.Status.RUNNING)) {
                imageViewAttack.setLayoutX(getLayoutX() - WIDTH/2);
                this.getChildren().add(imageViewAttack);
                attackSwipeLeft.play();
            }
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
            attackCount++;
            attack();
        } else {
            attackCount = 0;
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
        } else if (isAttacking && isRight) {
            animation = attackRight;
        } else if (isAttacking) {
            animation = attackLeft;
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



    @Override
    public void applyVelocity() {
        super.applyVelocity();
        if(!isKnockback) {
            setVelocity(new Point2D(0, velocity.getY()));
        }
    }
}

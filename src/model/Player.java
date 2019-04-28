package model;

import controllers.PauseMenuController;
import javafx.animation.Animation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player extends Entity {

    private static final double JUMPHEIGHT = -12.5 / Map.SCALE;
    private static final long DAMAGE_COOLDOWN = 800;
    private static final double RUNSPEED = 5 / Map.SCALE;
    private static final double ATTACKSLIDE = 8 / Map.SCALE;
    static final double WIDTH = 64;
    static final double HEIGHT = 64;
    IntegerProperty deathCount = new SimpleIntegerProperty();
    private SoundEffect walkSound = new SoundEffect("resources/sounds/walk.wav");
    private SoundEffect attackSound = new SoundEffect("resources/sounds/swish.wav");
    private SoundEffect damageSound = new SoundEffect("resources/sounds/damage.wav");

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
        currentWeapon = new WeaponSword(this);
        playerWeapons.add(currentWeapon);
    }

    //Cool effect for if the player is standing in water or something (creates reflection)

    void createSprite() {
        GameManager gm = (GameManager) getScene().getRoot();
        PauseMenuController pm = gm.getPlayingState().getPauseLoader().getController();
        pm.getHealthBar().progressProperty().bind(health);
        pm.getDeathCount().textProperty().bind(deathCount.asString());

        this.getChildren().addAll(playerSprite);
    }

    Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    private void crouch() {

    }

    private void jump() {
        if (!this.getInAir()) {
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

    private void moveLeft() {
        setVelocity(new Point2D(-RUNSPEED, getVelocity().getY()));
        this.setRight(false);
    }

    private void moveRight() {
        setVelocity(new Point2D(RUNSPEED, getVelocity().getY()));
        this.setRight(true);
    }

    private void attack() {
        if (attackCount == 1) {
            attackSound.playSound();
            isAttacking = true;
            velocity = new Point2D((isRight) ? ATTACKSLIDE : -ATTACKSLIDE, velocity.getY());
        }
    }

    private void switchWeapon() {

    }

    void knockBack(double xDistance, double yDistance, boolean isDamage) {
        isKnockback = true;
        setVelocity(new Point2D(xDistance, yDistance));
        if (isDamage) {
            damageSound.playSound();
            isFlashing = true;
            timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
        }
    }

    @Override
    public void move() {
        if (isKnockback) {
            if (Math.round(velocity.getX()) == 0) {
                isKnockback = false;
            } else {
                setVelocity(new Point2D((velocity.getX() > 0) ? velocity.getX() - 1 : velocity.getX() + 1, (velocity.getY() > 0) ? velocity.getY() - 1 : velocity.getY() + 1));
            }
            applyGravity();
            applyVelocity();
            return;
        }
        if (!this.isDying) {
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
                if (!inAir) {
                    walkSound.playSound();
                }
            }

            if (keysPressed.contains(controls.getRightKey())) {
                moveRight();
                if (!inAir) {
                    walkSound.playSound();
                }
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
        }

        applyGravity();
        applyVelocity();
        if (!playerSprite.isAttacking() && !playerSprite.isDamaged() && !isDying) {
            playAnimation();
        }
    }

    @Override
    public Bounds getBounds() {
        return new BoundingBox(playerSprite.getBounds().getMinX() + getTranslateX(),
                playerSprite.getBounds().getMinY() + getTranslateY(), playerSprite.getBounds().getWidth(), playerSprite.getBounds().getHeight());
    }

    void playAnimation() {
        if(isFlashing && isRight) {
            playerSprite.damageRight();
        } else if(isFlashing) {
            playerSprite.damageLeft();
        } else if (isAttacking && isRight) {
            playerSprite.attackRight();
        } else if (isAttacking) {
            playerSprite.attackLeft();
        } else if(!(controls.isAnyKeyPressed(keysPressed)) && !inAir && isRight) {
            playerSprite.standRight();
        } else if(!(controls.isAnyKeyPressed(keysPressed)) && !inAir) {
            playerSprite.standLeft();
        } else if(inAir && isRight) {
            playerSprite.jumpRight();
        } else if(inAir) {
            playerSprite.jumpLeft();
        } else if(keysPressed.contains(controls.getRightKey())) {
            playerSprite.walkRight();
        } else if(!inAir && keysPressed.contains(controls.getLeftKey())) {
            playerSprite.walkLeft();
        } else if (health.get() <= 0) {
            playerSprite.standRight();
        }

        if(!(playerSprite.getCurrentAnimation().getStatus() == Animation.Status.RUNNING)) {
            playerSprite.stopAll();
            playerSprite.getCurrentAnimation().play();
        }
    }

    @Override
    public void applyVelocity() {
        super.applyVelocity();
        if(!isKnockback) {
            setVelocity(new Point2D(0, velocity.getY()));
        }
    }

    void addDoubleJumpBoots() {
        playerSprite.addBoots();
        hasBoots = true;
    }

    void redFlash() {
        playerSprite.startFlashing();
    }

    void resetPlayer() {
        if (!isDying) {
            isDying = true;
            playerSprite.die();
            playerSprite.getCurrentAnimation().setOnFinished(e -> {
                isDying = false;
                deathCount.setValue(deathCount.get() + 1);
                health.setValue(1.0);
                velocity = new Point2D(0, 0);
                isFlashing = false;
                isAttacking = false;
                inAir = true;
                isRight = true;
                playerSprite.setAttacking(false);
                playerSprite.setDamaged(false);
                playAnimation();
                ((GameManager) this.getScene().getRoot()).restartLevel();
            });
        }
        if (isDying && !inAir) {
            playerSprite.getCurrentAnimation().play();
        }
    }
}


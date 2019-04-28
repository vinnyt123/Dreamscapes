package model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class WalkingEnemy extends Enemy {
    private static final double SPEED = 2/Map.SCALE;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 47;
    private static final double HEALTH = 0.32;
    private static final double DAMAGE = 0.1;
    private Bounds platformBounds;
    private static final Image SPRITE_SHEET = new Image("images/slime_sheet.png");
    private SoundEffect slimeSound = new SoundEffect("resources/sounds/slime.wav");
    private SpriteAnimation animation;
    private SpriteAnimation walkLeft;
    private SpriteAnimation walkRight;
    private SpriteAnimation dieRight;
    private SpriteAnimation dieLeft;
    private SpriteAnimation attackRight;
    private SpriteAnimation attackLeft;
    private static final double KNOCKBACK_PLAYER = 8/Map.SCALE;
    private static final double KNOCKBACK_THIS = 3/Map.SCALE;
    private boolean movingRight = true;
    private WalkingEnemySpawner walkingEnemySpawner;



    WalkingEnemy(Player player, WalkingEnemySpawner walkingEnemySpawner) {
        super(player);
        this.walkingEnemySpawner = walkingEnemySpawner;
        knockback_player = KNOCKBACK_PLAYER;
        knockback_this = KNOCKBACK_THIS;
        damage = DAMAGE;
        health.setValue(HEALTH);

        createSprite();
        velocity = new Point2D(SPEED, 0);
    }


    private void createSprite() {
        imageView = new ImageView(SPRITE_SHEET);
        imageView.setEffect(colorAdjust);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 36));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        walkLeft = new SpriteAnimation(imageView, Duration.millis(200), 4, 4, 32, 25, 0);
        walkRight = new SpriteAnimation(imageView, Duration.millis(200), 4, 4, 32, 25, 25);
        dieLeft = new SpriteAnimation(imageView, Duration.millis(1000), 4, 4, 32, 25, 50);
        dieRight = new SpriteAnimation(imageView, Duration.millis(1000), 4, 4, 32, 25, 125);
        attackLeft = new SpriteAnimation(imageView, Duration.millis(500), 5, 5, 32, 25, 75);
        attackRight = new SpriteAnimation(imageView, Duration.millis(500), 5, 5, 32, 25, 100);
        animation = walkLeft;
        animation.setCycleCount(1);
        this.getChildren().add(imageView);
    }

    public void move() {
        if (isKnockback) {
            knockBack();
        } else if(isDying) {
            setVelocity(new Point2D(0, velocity.getY()));
            applyGravity();
            applyVelocity();
            return;
        }

        if (!isKnockback) {
            if (platformBounds != null) {
                if ((movingRight && (this.getBoundsInParent().getMaxX() > platformBounds.getMaxX())) || (movingRight && (this.getBoundsInParent().getMaxX() > platformBounds.getMaxX() + WIDTH))) {
                    movingRight = false;
                } else if ((!movingRight && this.getBoundsInParent().getMinX() < platformBounds.getMinX()) || (!movingRight && this.getBoundsInParent().getMinX() < platformBounds.getMinX() - this.width)) {
                    movingRight = true;
                }

                if (movingRight) {
                    velocity = new Point2D(SPEED, velocity.getY());
                } else {
                    velocity = new Point2D(-SPEED, velocity.getY());
                }

                if (isFlashing) {
                    velocity = new Point2D(0, velocity.getY());
                }
            }
        }



        Point2D playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));

        playAnimation();
        applyGravity();
        applyVelocity();
    }

    @Override
    public Bounds getBounds() {
        return new BoundingBox(getBoundsInParent().getMinX() + 8, getBoundsInParent().getMinY() + 10, getBoundsInParent().getWidth() - 18, getBoundsInParent().getHeight() - 15);
    }

    public void updatePlatform(Bounds bounds) {
        platformBounds = bounds;
    }

    public void changeDirection() {
        movingRight = !movingRight;
    }

    private void playAnimation() {
        if(isAttacking && movingRight) {
            animation = attackRight;
            animation.setOnFinished(e -> isAttacking = false);
        } else if(isAttacking) {
            animation = attackLeft;
            animation.setOnFinished(e -> isAttacking = false);
        } else if(movingRight) {
            animation = walkRight;
        } else {
            animation = walkLeft;
        }

        animation.play();
    }

    public void deadAnimation() {
        slimeSound.playSound();
        isDying = true;
        if(movingRight) {
            animation = dieRight;
        } else {
            animation = dieLeft;
        }
        animation.setOnFinished(e -> isDead = true);
        animation.play();
    }


    void decrementSpawnerCount() {
        if (walkingEnemySpawner != null) {
            walkingEnemySpawner.enemyDied();
        }
    }

    boolean getMovingRight() {
        return movingRight;
    }
}

package model;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WalkingEnemy extends Enemy {
    private static final double SPEED = 2;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    private static final double HEALTH = 10;
    private static final double DAMAGE = 0.1;
    private Bounds platformBounds;
    private static final Image SPRITE_SHEET = new Image("images/fly_sheet.png");
    private SpriteAnimation animation;
    private SpriteAnimation flyRight;
    private static final double KNOCKBACK_PLAYER = 8;
    private static final double KNOCKBACK_THIS = 9;
    private SpriteAnimation flyLeft;
    private boolean movingRight = true;
    private boolean isFalling = false;


    public WalkingEnemy(Rectangle platform, Player player) {
        super(player);
        knockback_player = KNOCKBACK_PLAYER;
        knockback_this = KNOCKBACK_THIS;
        damage = DAMAGE;
        this.platformBounds = platform.getBoundsInParent();
        health.setValue(HEALTH);
        this.spawnAt(new Point2D(platformBounds.getMinX() + platformBounds.getWidth() / 2 - WIDTH / 2, platformBounds.getMinY() - HEIGHT - 0.1));
        createSprite();
        velocity = new Point2D(SPEED, 0);
    }

    private void createSprite() {
        imageView = new ImageView(SPRITE_SHEET);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 36));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        flyLeft = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 36, 0);
        flyRight = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 36, 36);
        animation = flyLeft;
        animation.getImageView().setEffect(colorAdjust);
        animation.setCycleCount(1);
        this.getChildren().add(imageView);
    }

    public void move() {
        System.out.println(isKnockback);
        System.out.println(getTranslateX() + " " + getTranslateY());
        System.out.println(velocity);
        System.out.println("XXXXXXX");

        if (velocity.getY() == 0) {
            isFalling = false;
        } else {
            isFalling = true;
        }

        if (isKnockback) {
            knockBack();
        }

        if (!isKnockback) {
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

            if (player.isFlashing) {
                velocity = new Point2D(0,velocity.getY());
            }
        }



        Point2D playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));

        playAnimation();
        applyGravity();
        applyVelocity();
    }

    public void updatePlatform(Bounds bounds) {
        platformBounds = bounds;
    }

    public void changeDirection() {
        movingRight = !movingRight;
    }

    private void playAnimation() {
        if(this.getVelocity().getX() > 0) {
            animation = flyRight;
        } else {
            animation = flyLeft;
        }
        animation.play();
    }
}

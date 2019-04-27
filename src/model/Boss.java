package model;

import javafx.animation.Animation;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Boss extends Enemy {

    private static final Image SPRITE_SHEET = new Image("images/boss_sheet.png");
    private static final double WIDTH = 220;
    private static final double HEIGHT = 220;
    private static final double HEALTH = 1.0;
    private double SPEED = 3;

    private SpriteAnimation animation;

    private SpriteAnimation walkLeft;
    private SpriteAnimation walkRight;
    private SpriteAnimation dieRight;
    private SpriteAnimation dieLeft;
    private SpriteAnimation attack1Right;
    private SpriteAnimation attack1Left;
    private SpriteAnimation attack2Right;
    private SpriteAnimation attack2Left;
    private SpriteAnimation attack3Right;
    private SpriteAnimation attack3Left;
    private SpriteAnimation damageRight;
    private SpriteAnimation damageLeft;

    private static final double DAMAGE1 = 0.1;
    private static final double DAMAGE2 = 0.1;
    private static final double DAMAGE3 = 0.3;
    private static final double KNOCKBACK_PLAYER = 24;
    private static final double KNOCKBACK_THIS = 8;
    private ProgressBar bossBar = new ProgressBar();
    private int attackIndex = -1;
    private Point2D playerPos;
    private List<SpriteAnimation> attacks = new ArrayList<>();
    private List<Double> damages = new ArrayList<>(Arrays.asList(DAMAGE1, DAMAGE2, DAMAGE3));
    private boolean hasAttacked = false;

    public Boss(Player player, double spawnX, double spawnY) {
        super(player);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        damage = DAMAGE1;
        knockback_this = KNOCKBACK_THIS;
        knockback_player = KNOCKBACK_PLAYER;
        health.setValue(HEALTH);
        velocity = new Point2D(SPEED, 0);
        createSprite();
    }

    //TODO: make sprite not blurry by scaling the image in photoshop then using that, rather than scaling in java
    private void createSprite() {
        imageView = new ImageView(SPRITE_SHEET);
        imageView.setSmooth(false);
        imageView.setEffect(colorAdjust);
        imageView.setViewport(new Rectangle2D(0, 0, 96, 66));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        walkRight = new SpriteAnimation(imageView, Duration.millis(400), 8, 8, 96, 96, 96);
        walkLeft = new SpriteAnimation(imageView, Duration.millis(400), 8, 8, 96, 96, 1056);
        dieRight = new SpriteAnimation(imageView, Duration.millis(1000), 6, 6, 96, 96, 864);
        dieLeft = new SpriteAnimation(imageView, Duration.millis(1000), 6, 6, 96, 96, 1824);
        attack1Right = new SpriteAnimation(imageView, Duration.millis(800), 9, 9, 96, 96, 288);
        attack1Left = new SpriteAnimation(imageView, Duration.millis(800), 9, 9, 96, 96, 1248);
        attack2Right = new SpriteAnimation(imageView, Duration.millis(600), 5, 5, 96, 96, 384);
        attack2Left = new SpriteAnimation(imageView, Duration.millis(600), 5, 5, 96, 96, 1344);
        attack3Right = new SpriteAnimation(imageView, Duration.millis(1000), 9, 9, 96, 96, 576);
        attack3Left = new SpriteAnimation(imageView, Duration.millis(1000), 9, 9, 96, 96, 1536);
        damageRight = new SpriteAnimation(imageView, Duration.millis(400), 3, 3, 96, 96, 768);
        damageLeft = new SpriteAnimation(imageView, Duration.millis(400), 3, 3, 96, 96, 1728);

        attacks.addAll(Arrays.asList(attack1Right, attack2Right, attack3Right, attack1Left, attack2Left, attack3Left));

        bossBar.setTranslateX(65);
        bossBar.setTranslateY(20);
        bossBar.setMaxWidth(100);
        bossBar.setMaxHeight(4);
        bossBar.progressProperty().bind(health);
        bossBar.setStyle("-fx-accent :  #A70505;" +
                "-fx-background-color :  transparent;");

        animation = dieRight;
        animation.setCycleCount(1);
        this.getChildren().add(imageView);
        this.getChildren().add(bossBar);
    }

    @Override
    public void move() {

        playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        isRight = playerPos.getX() > getTranslateX()+WIDTH/2;
        theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
        if(!isAttacking && !isDying && !isDead) {
            velocity = new Point2D((getTranslateX() + WIDTH/2 > player.getTranslateX() + Player.WIDTH/2)? -SPEED : SPEED, velocity.getY());
        } else {
            velocity = new Point2D(0, velocity.getY());
        }

        applyVelocity();
        applyGravity();

        if(!(animation.getStatus() == Animation.Status.RUNNING)) {
            playAnimation();
        }
    }

    void attack() {
        if(isAttacking && (playerPos.getX() > getTranslateX()-10 && playerPos.getX() < getTranslateX()+WIDTH) && playerPos.getY() + Player.WIDTH/2 > getTranslateY() + 50 && !hasAttacked) {
            player.redFlash();
            hasAttacked = true;
            player.health.setValue(player.health.getValue() - damage);
            setKnockBack(false);
            player.knockBack(Math.cos(Math.toRadians(theta)) * knockback_player, Math.sin(Math.toRadians(theta)) * knockback_player, true);
        }
    }

    private void playAnimation() {

        if(playerPos.getX() > getTranslateX()-10 && playerPos.getX() < getTranslateX()+WIDTH+10) {
            animation = getAttackAnimation();
            isAttacking = true;
        } else if(isRight && !isAttacking) {
            animation = walkRight;
        } else if (!isAttacking) {
            animation = walkLeft;
        }

        animation.setOnFinished(e -> {
            isAttacking = false;
            hasAttacked = false;
        });
        animation.play();
    }

    private SpriteAnimation getAttackAnimation() {
        attackIndex++;
        if(attackIndex > 2) {
            attackIndex = 0;
        }
        if(isRight) {
            return attacks.get(attackIndex);
        } else {
            return attacks.get(attackIndex + 3);
        }
    }

    @Override
    public Bounds getBounds() {
        return new BoundingBox(getBoundsInParent().getMinX() + 50, getBoundsInParent().getMinY() + 80, getBoundsInParent().getWidth() - 100, getBoundsInParent().getHeight() - 160);
    }

    @Override
    public void deadAnimation() {
        isDying = true;
        bossBar.setVisible(false);
        if(isRight) {
            animation = dieRight;
        } else {
            animation = dieLeft;
        }
        animation.setOnFinished(e -> {
            isDead = true;
            ((GameManager) this.getScene().getRoot()).endGame();
        });
        animation.play();
    }
}

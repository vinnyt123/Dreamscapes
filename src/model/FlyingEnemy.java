package model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double SPEED = 3;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 30;
    private static final double DAMAGE = 0.1;
    private static final double KNOCKBACK_PLAYER = 8;
    private static final double KNOCKBACK_THIS = 9;
    private static final double HEALTH = 10;
    private static final Image SPRITE_SHEET = new Image("images/fly_sheet.png");
    private SpriteAnimation animation;
    private SpriteAnimation flyRight;
    private SpriteAnimation flyLeft;

    private Player player;


    public FlyingEnemy(double spawnX, double spawnY, Player player) {
        super();
        this.player = player;
        knockback_player = KNOCKBACK_PLAYER;
        damage = DAMAGE;
        health.setValue(HEALTH);
        width = WIDTH;
        height = HEIGHT;
        knockback_this = KNOCKBACK_THIS;
        createSprite(spawnX, spawnY);
    }

    private void createSprite(double spawnX, double spawnY) {
        setTranslateX(spawnX);
        setTranslateY(spawnY);

        ImageView imageView = new ImageView(SPRITE_SHEET);
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

    @Override
    public void move() {
        if(isKnockback) {
            knockBack();
            return;
        }

        //Move flying enemy towards player
        Point2D playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        double distance = playerPos.distance(getTranslateX() + (WIDTH/2), getTranslateY() + (HEIGHT/2));
        if(distance < RANGE && !player.isFlashing()) {
            isRight = player.getTranslateX() > getTranslateX();
            theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
            this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));

        } else {
            this.setVelocity(new Point2D(0, 0));
        }

        applyVelocity();
        playAnimation();
    }

    private void playAnimation() {
        if(isRight) {
            animation = flyRight;
        } else {
            animation = flyLeft;
        }
        animation.play();
    }
}

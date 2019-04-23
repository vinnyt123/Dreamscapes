package model;

import javafx.geometry.Bounds;
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
    private static final double DAMAGE = 0.5;
    private static final double KNOCKBACK_PLAYER = 8;
    private static final double KNOCKBACK_THIS = 9;
    private static final double HEALTH = 10;
    private static final Image SPRITE_SHEET = new Image("images/fly_sheet.png");
    private SpriteAnimation animation;
    private SpriteAnimation flyRight;
    private SpriteAnimation flyLeft;
    private ImageView imageView;
    private SpriteAnimation dieRight;
    private SpriteAnimation dieLeft;


    public FlyingEnemy(double spawnX, double spawnY, Player player) {
        super(player);
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

        imageView = new ImageView(SPRITE_SHEET);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 36));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        flyLeft = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 36, 0);
        flyRight = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 36, 36);
        dieLeft = new SpriteAnimation(imageView, Duration.millis(400), 2, 2, 72, 36, 72);
        dieRight = new SpriteAnimation(imageView, Duration.millis(400), 2, 2, 72, 36, 108);
        animation = flyLeft;
        animation.getImageView().setEffect(colorAdjust);
        animation.setCycleCount(1);

        this.getChildren().add(imageView);
    }

    @Override
    public void move() {

        //System.out.println(getTranslateX());
        if(isKnockback) {
            knockBack();
            return;
        } else if(isDying) {
            setVelocity(new Point2D(0, velocity.getY()));
            applyGravity();
            applyVelocity();
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

    @Override
    public Bounds getBounds() {
        return this.getBoundsInParent();
    }

    private void playAnimation() {
        if(isRight) {
            animation = flyRight;
        } else {
            animation = flyLeft;
        }
        animation.play();
    }

    public void deadAnimation() {
        isDying = true;
        if(isRight) {
            animation = dieRight;
        } else {
            animation = dieLeft;
        }
        animation.play();
        timer.schedule(new dyingTimer(), Entity.DYING_TIME);
    }
}

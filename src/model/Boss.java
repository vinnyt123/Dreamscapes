package model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Boss extends Enemy {

    private static final Image SPRITE_SHEET = new Image("images/boss_sheet.png");
    private static final double WIDTH = 220;
    private static final double HEIGHT = 220;
    private static final double HEALTH = 50;
    private double SPEED = 2;

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
    private static final double DAMAGE2 = 0.2;
    private static final double DAMAGE3 = 0.3;
    private static final double KNOCKBACK_PLAYER = 8;
    private static final double KNOCKBACK_THIS = 5;
    private boolean movingRight = false;

    public Boss(Player player, double spawnX, double spawnY) {
        super(player);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        health.setValue(HEALTH);
        velocity = new Point2D(SPEED, 0);
        createSprite();
    }

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

        animation = damageRight;
        animation.setCycleCount(1);
        this.getChildren().add(imageView);
    }

    @Override
    public void move() {
        animation.play();
    }

    @Override
    public Bounds getBounds() {
        return new BoundingBox(getBoundsInParent().getMinX() + 10, getBoundsInParent().getMinY() + 10, getBoundsInParent().getWidth() - 20, getBoundsInParent().getHeight() - 20);
    }

    @Override
    public void deadAnimation() {

    }
}

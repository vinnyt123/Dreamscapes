package model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WalkingEnemySpawner extends Enemy {

    private static final Image SPRITE_SHEET_RIGHT = new Image("images/Spawner.png");
    private static final Image SPRITE_SHEET_LEFT = new Image("images/SpawnerLeft.png");
    private SoundEffect spawnSound = new SoundEffect("resources/sounds/spawn.wav");

    private static final double WIDTH = 138;
    private static final double HEIGHT = 116;
    private static final double HEALTH = 0.32;

    private int count = 0;
    private double secondsBetweenSpawn = 1;
    private final int maxNumberOfEnemies = 20;
    private int numberOfEnemies = 0;
    private boolean isFacingLeft;
    private final double SPAWN_HEIGHT = -5/Map.SCALE;
    private final double SPAWN_XVEL = 5/Map.SCALE;
    private Point2D range;
    private Wall wall = null;
    private StackPane stackPane;
    private FadeTransition deadAnimation;
    private final int DYING_TIME = 1000;
    private Bounds bounds;


    WalkingEnemySpawner(Player player, boolean isFacingLeft, Point2D range) {
        super(player);
        this.range = range;
        this.isFacingLeft = isFacingLeft;
        knockback_player = 0;
        knockback_this = 0;
        damage = 0;
        health.setValue(HEALTH);
        createSprite();
    }

    private void createSprite() {
        imageView = new ImageView();
        if (isFacingLeft) {
            imageView.setImage(SPRITE_SHEET_LEFT);
        } else {
            imageView.setImage(SPRITE_SHEET_RIGHT);
        }
        imageView.setEffect(colorAdjust);
        imageView.setViewport(new Rectangle2D(0, 0, 138, 116));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        wall = new Wall(new Rectangle(WIDTH, HEIGHT), true);
        wall.setVisible(false);

        deadAnimation = new FadeTransition(Duration.millis(DYING_TIME), this);
        deadAnimation.setFromValue(1.0);
        deadAnimation.setToValue(0);
        deadAnimation.setCycleCount(1);
        this.getChildren().add(imageView);
    }

    @Override
    public void move() {
    }


    WalkingEnemy update() {

        double distanceX = player.getTranslateX() - this.getTranslateX();
        double distanceY = player.getTranslateY() - this.getTranslateY();
        boolean withinRangeX = Math.abs(distanceX) < range.getX();
        boolean withinRangeY = Math.abs(distanceY) < range.getY();

        if (withinRangeX && withinRangeY) {
            count++;
            if (count % (secondsBetweenSpawn * 60) == 0 && numberOfEnemies < maxNumberOfEnemies) {
                numberOfEnemies++;
                WalkingEnemy newWalkingEnemy = new WalkingEnemy(player, this);
                spawnSound.playSound();
                if (isFacingLeft) {
                    newWalkingEnemy.spawnAt(new Point2D(this.getBoundsInParent().getMinX(), this.getBoundsInParent().getMinY() + this.getBoundsInParent().getHeight() / 2));
                    newWalkingEnemy.changeDirection();
                    newWalkingEnemy.setVelocity(new Point2D(-SPAWN_XVEL, SPAWN_HEIGHT));
                } else {
                    newWalkingEnemy.spawnAt(new Point2D(this.getBoundsInParent().getMaxX(), this.getBoundsInParent().getMinY() + this.getBoundsInParent().getHeight() / 2));
                    newWalkingEnemy.setVelocity(new Point2D(SPAWN_XVEL, SPAWN_HEIGHT));
                }
                return newWalkingEnemy;
            }
        } else {
            count = 0;
        }
        return null;
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void deadAnimation() {
        isDying = true;
        if (deadAnimation.getStatus() != Animation.Status.RUNNING) {
            deadAnimation.play();
        }
        timer.schedule(new dyingTimer(), DYING_TIME);
    }

    @Override
    public void intersect(Player player) {
        wall.intersect(player);
    }

    void enemyDied() {
        numberOfEnemies--;
        count = 0;
    }

    void setUpCollisions() {
        bounds = new BoundingBox(this.getBoundsInParent().getMinX(), this.getBoundsInParent().getMinY(), WIDTH, HEIGHT - 25);
        wall.setBounds(new BoundingBox(this.getBoundsInParent().getMinX(), this.getBoundsInParent().getMinY(), WIDTH - 70, HEIGHT));
    }
}

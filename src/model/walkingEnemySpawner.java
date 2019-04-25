package model;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class walkingEnemySpawner extends Enemy {

    private static final Image SPRITE_SHEET_RIGHT = new Image("images/Spawner.png");
    private static final Image SPRITE_SHEET_LEFT = new Image("images/SpawnerLeft.png");

    private static final double WIDTH = 138;
    private static final double HEIGHT = 116;
    private static final double HEALTH = 10;

    private int count = 0;
    private int secondsBetweenSpawn = 4;
    private int maxNumberOfEnemies = 5;
    private int numberOfEnemies = 0;
    private boolean isFacingLeft = true;
    private final int SPAWN_HEIGHT = 5;

    public walkingEnemySpawner(Player player, boolean isFacingLeft) {
        super(player);
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
        this.getChildren().add(imageView);
    }

    @Override
    public void move() {
    }

    public WalkingEnemy update() {
        count++;

        if (count % (secondsBetweenSpawn * 60) == 0 && numberOfEnemies < maxNumberOfEnemies) {
            numberOfEnemies++;
            WalkingEnemy newWalkingEnemy = new WalkingEnemy(player, this);
            newWalkingEnemy.setVelocity(new Point2D(0, SPAWN_HEIGHT));
            if (isFacingLeft) {
                newWalkingEnemy.spawnAt(new Point2D(this.getBoundsInParent().getMinX(), this.getBoundsInParent().getMinY() + this.getBoundsInParent().getHeight()/2));
                newWalkingEnemy.changeDirection();
            } else {
                newWalkingEnemy.spawnAt(new Point2D(this.getBoundsInParent().getMaxX(), this.getBoundsInParent().getMinY() + this.getBoundsInParent().getHeight()/2));
            }
            return newWalkingEnemy;
        }
        return null;
    }

    @Override
    public Bounds getBounds() {
        return this.getBoundsInParent();
    }

    @Override
    public void deadAnimation() {

    }

    public void enemyDied() {
        numberOfEnemies--;
        count = 0;
    }
}

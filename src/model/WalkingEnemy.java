package model;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class WalkingEnemy extends Enemy {
    private static final double SPEED = 2;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    private Bounds platformBounds;


    public WalkingEnemy(Rectangle platform) {
        super();
        this.platformBounds = platform.getBoundsInParent();
        createSprite(platformBounds.getMinX() + platformBounds.getWidth() / 2 - WIDTH / 2, platformBounds.getMinY() - HEIGHT);
        velocity = new Point2D(SPEED, 0);
    }

    private void createSprite(double spawnX, double spawnY) {
        Rectangle body = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        this.getChildren().add(body);
    }

    public void move(List<Rectangle> walls) {
        if (this.getBoundsInParent().getMaxX() > platformBounds.getMaxX() || this.getBoundsInParent().getMinX() < platformBounds.getMinX()) {
            velocity = new Point2D(-velocity.getX(), 0);
        }
        setTranslateX(getTranslateX() + velocity.getX());
        setTranslateY(getTranslateY() + velocity.getY());
        }
}

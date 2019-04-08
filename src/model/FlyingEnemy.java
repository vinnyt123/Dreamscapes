package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double ATTACKING_RANGE = 200;
    private static final double ATTACKING_SPEED = 10;
    private static final double SPEED = 4;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;

    private double theta;
    private Player player;

    public FlyingEnemy(double spawnX, double spawnY, Player player) {
        super();
        createSprite(spawnX, spawnY);
        this.player = player;
    }

    private void createSprite(double spawnX, double spawnY) {
        Rectangle body = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        this.getChildren().add(body);
    }

    public void move() {
        Point2D playerPos = player.getPosition();
        double distance = playerPos.distance(getTranslateX() + 15, getTranslateY() + 15);
        if(distance < RANGE) {
            if (distance > ATTACKING_RANGE) {
                theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT / 2), playerPos.getX() - this.getTranslateX() - (WIDTH / 2)));
                this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));
            } else {
                velocity = new Point2D(ATTACKING_SPEED *  Math.cos(Math.toRadians(theta)), ATTACKING_SPEED *  Math.sin(Math.toRadians(theta)));
            }
        } else {
            this.setVelocity(new Point2D(0, 0));
        }
        super.translateThis();
    }

}

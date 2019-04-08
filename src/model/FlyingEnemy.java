package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double SPEED = 4;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;

    public FlyingEnemy(double spawnX, double spawnY) {
        super();
        createSprite(spawnX, spawnY);
    }

    private void createSprite(double spawnX, double spawnY) {
        Rectangle body = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        this.getChildren().add(body);
    }

    public void move(List<Rectangle> walls, Point2D playerPos) {
        double distance = playerPos.distance(getTranslateX() + 15, getTranslateY() + 15);
        if(distance < RANGE) {
            double theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
            this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));

        } else {
            this.setVelocity(new Point2D(0, 0));
        }

        super.move(walls);
    }

}

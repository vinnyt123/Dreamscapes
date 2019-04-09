package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double SPEED = 4;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    private static final double DAMAGE = 2;

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

    public void move(List<Rectangle> walls, Player player) {
        //Move flying enemy towards player
        Point2D playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        double distance = playerPos.distance(getTranslateX() + (WIDTH/2), getTranslateY() + (HEIGHT/2));
        if(distance < RANGE) {
            double theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
            this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));

        } else {
            this.setVelocity(new Point2D(0, 0));
        }

        if(this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            //knock-back player
        }
        super.move(walls);
    }
}

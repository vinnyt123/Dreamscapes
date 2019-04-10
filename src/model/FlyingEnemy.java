package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double SPEED = 4;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    private static final double DAMAGE = 2;
    private static final double KNOCKBACK = 7;
    private static final double HEALTH = 10;

    private Rectangle body;

    private Player player;


    public FlyingEnemy(double spawnX, double spawnY,Player player) {
        super();
        this.player = player;
        health = HEALTH;
        createSprite(spawnX, spawnY);
    }

    private void createSprite(double spawnX, double spawnY) {
        body = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        this.getChildren().add(body);
    }

    @Override
    public void move() {
        //Move flying enemy towards player
        Point2D playerPos = new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2));
        double distance = playerPos.distance(getTranslateX() + (WIDTH/2), getTranslateY() + (HEIGHT/2));
        if(distance < RANGE && !player.isKnockback) {
            double theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
            this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));

        } else {
            this.setVelocity(new Point2D(0, 0));
        }

        if(this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            player.health -= 5;
            player.knockBack(this.getVelocity().getX() > 0, KNOCKBACK);
            this.getChildren().remove(body);
        }

        applyVelocity();
    }
}

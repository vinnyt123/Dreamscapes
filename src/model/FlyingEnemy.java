package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class FlyingEnemy extends Enemy {

    private static final double RANGE = 500;
    private static final double SPEED = 3;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    private static final double DAMAGE = 4;
    private static final double KNOCKBACK_PLAYER = 8;
    private static final double KNOCKBACK_THIS = 8;
    private static final double HEALTH = 10;
    private boolean knockX = false;
    private boolean knockY = false;
    private double theta;

    private Player player;


    public FlyingEnemy(double spawnX, double spawnY, Player player) {
        super();
        this.player = player;
        health = HEALTH;
        createSprite(spawnX, spawnY);
    }

    private void createSprite(double spawnX, double spawnY) {
        Rectangle body = new Rectangle(WIDTH, HEIGHT);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        this.getChildren().add(body);
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
            theta = Math.toDegrees(Math.atan2(playerPos.getY() - this.getTranslateY() - (HEIGHT/2),  playerPos.getX() - this.getTranslateX() - (WIDTH/2)));
            this.setVelocity(new Point2D(SPEED * Math.cos(Math.toRadians(theta)), SPEED * Math.sin(Math.toRadians(theta))));

        } else {
            this.setVelocity(new Point2D(0, 0));
        }

        //If enemy hits player, give some damage and knock both back
        if(this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            player.health -= DAMAGE;
            setKnockBack(-Math.cos(Math.toRadians(theta)) * KNOCKBACK_THIS, -Math.sin(Math.toRadians(theta)) * KNOCKBACK_THIS);
            player.knockBack(Math.cos(Math.toRadians(theta)) * KNOCKBACK_PLAYER, Math.sin(Math.toRadians(theta)) * KNOCKBACK_PLAYER);
        }
        applyVelocity();
    }

    private void knockBack() {
        if(Math.round(velocity.getX()) == 0) {
            knockX = true;
        }
        if(Math.round(velocity.getY()) == 0) {
            knockY = true;
        }
        if(knockX && knockY) {
            isKnockback = false;
            knockX = false;
            knockY = false;
        }
        else {
            setVelocity(new Point2D((velocity.getX() > 0) ? velocity.getX() - 1 : velocity.getX() + 1, (velocity.getY() > 0) ? velocity.getY() - 1 : velocity.getY() + 1));
        }
        applyVelocity();
    }

    private void setKnockBack(double xDistance, double yDistance) {
        isKnockback = true;
        setVelocity(new Point2D(xDistance, yDistance));
    }
}

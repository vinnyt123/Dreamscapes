package model;

import javafx.geometry.Point2D;

public abstract class Enemy extends Entity {

    double damage;
    double knockback_player;
    double knockback_this;
    double theta;
    private boolean knockX = false;
    private boolean knockY = false;

    public abstract void move();

    void knockBack() {
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

    void intersect(Player player) {
        if(this.getBoundsInParent().intersects(player.getBoundsInParent()) && !player.isFlashing()) {
            player.health -= damage;
            setKnockBack(-Math.cos(Math.toRadians(theta)) * knockback_this, -Math.sin(Math.toRadians(theta)) * knockback_this);
            player.knockBack(Math.cos(Math.toRadians(theta)) * knockback_player, Math.sin(Math.toRadians(theta)) * knockback_player);
        }
    }

}

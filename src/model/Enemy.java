package model;

import javafx.geometry.Point2D;

public abstract class Enemy extends Entity {

    double damage;
    double knockback_player;
    double knockback_this;
    double theta;
    double width;
    double height;
    private boolean knockX = false;
    private boolean knockY = false;
    private static long DAMAGE_COOLDOWN = 200;

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

    void setKnockBack(boolean isDamage) {
        isKnockback = true;
        colorAdjust.setSaturation(1);
        if(isDamage) {
            isFlashing = true;
            timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this * 1.5, -Math.sin(Math.toRadians(theta)) * knockback_this * 1.5));
        } else {
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this, -Math.sin(Math.toRadians(theta)) * knockback_this));
        }
    }

    void intersect(Player player) {
        if(this.getBoundsInParent().intersects(player.getBoundsInParent()) && !player.isFlashing()) {
            player.health.setValue(player.health.getValue() - damage);
            setKnockBack(false);
            player.knockBack(Math.cos(Math.toRadians(theta)) * knockback_player, Math.sin(Math.toRadians(theta)) * knockback_player);
        }
    }

}

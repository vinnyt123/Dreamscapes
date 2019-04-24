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
    Player player;

    public abstract void move();
    public abstract void deadAnimation();

    public Enemy(Player player) {
        this.player = player;
    }

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
        } else {
            if (velocity.getX() > 0) {
                velocity = new Point2D(velocity.getX() - 1,velocity.getY());
            } else if (velocity.getX() < 0) {
                velocity = new Point2D(velocity.getX() + 1,velocity.getY());
            }

            if (velocity.getY() > 0) {
                velocity = new Point2D(velocity.getX(), velocity.getY() - 1);
            } else if (velocity.getY() < 0) {
                velocity = new Point2D(velocity.getX(),velocity.getY() + 1);
            }

            //setVelocity(new Point2D((velocity.getX() > 0) ? velocity.getX() - 1 : velocity.getX() + 1, (velocity.getY() > 0) ? velocity.getY() - 1 : velocity.getY() + 1));
        }
        applyVelocity();
    }

    void setKnockBack(boolean isDamage) {
        isKnockback = true;
        if(isDamage) {
            isFlashing = true;
            timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this * 1.5, -Math.sin(Math.toRadians(theta)) * knockback_this * 1.5));
        } else {
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this, -Math.sin(Math.toRadians(theta)) * knockback_this));
        }
        if (this instanceof WalkingEnemy) {
            if (velocity.getX() > 0) {
                ((WalkingEnemy) this).setMovingRight(true);
            } else {
                ((WalkingEnemy) this).setMovingRight(false);
            }
        }
    }

    void intersect(Player player) {
        if(this.getBoundsInParent().intersects(player.getBoundsInParent()) && !player.isFlashing && !isDying && !isDead) {
            player.redFlash();
            player.health.setValue(player.health.getValue() - damage);
            setKnockBack(false);
            player.knockBack(Math.cos(Math.toRadians(theta)) * knockback_player, Math.sin(Math.toRadians(theta)) * knockback_player, true);
        }
    }

}

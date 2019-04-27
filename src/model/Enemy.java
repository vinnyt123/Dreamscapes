package model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public abstract class Enemy extends Entity {

    double damage;
    double knockback_player;
    double knockback_this;
    double theta;
    double width;
    double height;
    private boolean knockX = false;
    private boolean knockY = false;
    private static long DAMAGE_COOLDOWN = 1000;
    boolean isAttacking = false;
    ImageView imageView;
    ColorAdjust colorAdjust = new ColorAdjust();
    Player player;
    Timeline flashing;

    public abstract void move();
    public abstract void deadAnimation();

    public Enemy(Player player) {
        this.player = player;
        flashing = new Timeline();
        flashing.getKeyFrames().addAll(new KeyFrame(Duration.millis(100),
                new KeyValue(colorAdjust.brightnessProperty(), -1)), new KeyFrame(Duration.millis(100),
                new KeyValue(colorAdjust.brightnessProperty(), 0)));
        flashing.setCycleCount(3);
        flashing.setOnFinished(e -> colorAdjust.setBrightness(0));
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
        }
        applyVelocity();
    }

    void setKnockBack(boolean isDamage) {
        isKnockback = true;
        isFlashing = true;
        timer.schedule(new coolDownTimer(), DAMAGE_COOLDOWN);
        if(isDamage) {
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this * 1.5, -Math.sin(Math.toRadians(theta)) * knockback_this * 1.5));
        } else {
            setVelocity(new Point2D(-Math.cos(Math.toRadians(theta)) * knockback_this, -Math.sin(Math.toRadians(theta)) * knockback_this));
        }
    }

    void intersect(Player player) {
        if(this.getBounds().intersects(player.getBounds()) && !player.isFlashing && !isDying && !isDead) {
            if (damage > 0) {
                isAttacking = true;
                player.redFlash();
                player.health.setValue(player.health.getValue() - damage);
                setKnockBack(false);
                player.knockBack(Math.cos(Math.toRadians(theta)) * knockback_player, Math.sin(Math.toRadians(theta)) * knockback_player, true);
            }
        } else if(this instanceof Boss) {
            ((Boss) this).attack();
        }
    }

    void redFlash() {
            if (!flashing.getStatus().equals(Animation.Status.RUNNING)) {
                flashing.playFromStart();
            }
    }


    void remove() {
        this.getChildren().remove(imageView);
    }

}

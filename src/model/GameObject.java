package model;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public abstract class GameObject extends Group {

    public abstract void intersect(Entity entity);
    private static double EXTRA_LIL_TRANSLATION = 0.01;

    boolean moveX(Entity entity) {
        double xDistance = entity.getLastMove().getX();
        boolean movingRight = xDistance > 0;

        entity.setTranslateX(entity.getTranslateX() + entity.getLastMove().getX());
        if(getBoundsInParent().intersects(entity.getBounds())) {
            if(movingRight) {
                entity.setTranslateX(entity.getTranslateX() - (entity.getBounds().getMaxX() - this.getBoundsInParent().getMinX() + EXTRA_LIL_TRANSLATION));
            } else {
                entity.setTranslateX(entity.getTranslateX() - (entity.getBounds().getMinX() - this.getBoundsInParent().getMaxX() - EXTRA_LIL_TRANSLATION));
            }
            if (entity instanceof WalkingEnemy) {
                ((WalkingEnemy) entity).changeDirection();
            }
            entity.setVelocity(new Point2D(0, entity.getVelocity().getY()));
        }
        return movingRight;
    }

    void moveY(Entity entity) {
        double yDistance = entity.getLastMove().getY();
        boolean movingDown = yDistance > 0;
        entity.setTranslateY(entity.getTranslateY() + entity.getLastMove().getY());
        if (getBoundsInParent().intersects(entity.getBounds())) {
            if (movingDown) {
                entity.setInAir(false);
                entity.setDoubleJumped(false);
                entity.setTranslateY(entity.getTranslateY() - (entity.getBounds().getMaxY() - this.getBoundsInParent().getMinY() + EXTRA_LIL_TRANSLATION));
            } else {
                entity.setTranslateY(entity.getTranslateY() - (entity.getBounds().getMinY() - this.getBoundsInParent().getMaxY() - EXTRA_LIL_TRANSLATION));
            }
            entity.setVelocity(new Point2D(entity.velocity.getX(), 0));

            if (entity instanceof WalkingEnemy) {
                ((WalkingEnemy) entity).updatePlatform(this.getBoundsInParent());
            }
        }
    }
}

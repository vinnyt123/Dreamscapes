package model;


import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Wall extends GameObject {

    public Wall(Rectangle rectangle) {
        this.getChildren().add(rectangle);
    }

    @Override
    public void intersect(Entity entity) {
        if(getBoundsInParent().intersects(entity.getBoundsInParent())) {
            entity.undoMove();
            moveX(entity);
            moveY(entity);
        }
    }

    //TODO: Fix corner bug when you jump without moving left or right
    //Move x & y methods automatically do collisions: only move in increments of 1 until hit a wall then stop
    private void moveX(Entity entity) {
        double xDistance = entity.getVelocity().getX();
        boolean movingRight = xDistance > 0;

        if(getBoundsInParent().intersects(entity.getBoundsInParent())) {
            if(movingRight) {
                entity.setTranslateX(entity.getTranslateX() - (entity.getBoundsInParent().getMaxX() - this.getBoundsInParent().getMinX() - 0.01));
            } else {
                entity.setTranslateX(entity.getTranslateX() + (entity.getBoundsInParent().getMinX() - this.getBoundsInParent().getMaxX() + 0.01));
            }
        }
    }

    private void moveY(Entity entity) {
        double yDistance = entity.getVelocity().getY();
        boolean movingDown = yDistance > 0;

        if (getBoundsInParent().intersects(this.getBoundsInParent())) {
            if (movingDown) {
                if (entity instanceof Player) {
                    ((Player) entity).setInAir(false);
                }
                entity.setTranslateY(entity.getTranslateY() - (entity.getBoundsInParent().getMaxY() - this.getBoundsInParent().getMinY() + 0.01));
            } else {
                entity.setTranslateY(entity.getTranslateY() - (this.getBoundsInParent().getMaxY() - entity.getBoundsInParent().getMinY() + 0.01));
            }

        } else {
            if (entity instanceof Player) {
                //((Player) this).setInAir(true);
            }
        }
    }
}

package model;


import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Wall extends GameObject {

    public Wall(Rectangle rectangle) {
        Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());
        //newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(newRectangle);
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
    private boolean moveX(Entity entity) {
        double xDistance = entity.getLastMove().getX();
        boolean movingRight = xDistance > 0;

        entity.setTranslateX(entity.getTranslateX() + entity.getLastMove().getX());
        if(getBoundsInParent().intersects(entity.getBoundsInParent())) {
            System.out.println("XXXXXX");
            if(movingRight) {
                System.out.println("moving right");
                entity.setTranslateX(entity.getTranslateX() - (entity.getBoundsInParent().getMaxX() - this.getBoundsInParent().getMinX() + 1));
            } else {
                System.out.println("moving left");
                entity.setTranslateX(entity.getTranslateX() - (entity.getBoundsInParent().getMinX() - this.getBoundsInParent().getMaxX() - 1));
            }
            return true;
        }
        return false;
    }

    private void moveY(Entity entity) {
        double yDistance = entity.getLastMove().getY();
        boolean movingDown = yDistance > 0;

        entity.setTranslateY(entity.getTranslateY() + entity.getLastMove().getY());
        if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
            if (movingDown) {
                System.out.println("moving down");
                if (entity instanceof Player) {
                    ((Player) entity).setInAir(false);
                }
                entity.setTranslateY(entity.getTranslateY() - (entity.getBoundsInParent().getMaxY() - this.getBoundsInParent().getMinY() + 1));
            } else {
                System.out.println("moving up");
                entity.setTranslateY(entity.getTranslateY() - (entity.getBoundsInParent().getMinY() - this.getBoundsInParent().getMaxY() - 1));
            }
            entity.setVelocity(new Point2D(entity.velocity.getX(), 0));
        } else {
            if (entity instanceof Player) {
                //((Player) this).setInAir(true);
            }
        }
    }
}

package model;


import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Wall extends GameObject {

    private static double EXTRA_LIL_TRANSLATION = 0.01;

    public Wall(Rectangle rectangle) {
        Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());
        newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(newRectangle);
    }

    @Override
    public void intersect(Entity entity) {
        if(getBoundsInParent().intersects(entity.getBounds())) {
            entity.undoMove();
            moveX(entity);
            moveY(entity);
        } else if(entity.getVelocity().getY() > Map.GRAVITY) {
            entity.setInAir(true);
        }
    }

    private boolean moveX(Entity entity) {
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
                System.out.println(entity.velocity);
            }
            entity.setVelocity(new Point2D(0, entity.getVelocity().getY()));
            return true;
        }
        return false;
    }

    private void moveY(Entity entity) {
        double yDistance = entity.getLastMove().getY();
        boolean movingDown = yDistance > 0;
        entity.setTranslateY(entity.getTranslateY() + entity.getLastMove().getY());
        if (getBoundsInParent().intersects(entity.getBounds())) {
            if (movingDown) {
                //System.out.println("moving down");
                entity.setInAir(false);
                entity.setDoubleJumped(false);
                entity.setTranslateY(entity.getTranslateY() - (entity.getBounds().getMaxY() - this.getBoundsInParent().getMinY() + EXTRA_LIL_TRANSLATION));
            } else {
                //System.out.println("moving up");
                entity.setTranslateY(entity.getTranslateY() - (entity.getBounds().getMinY() - this.getBoundsInParent().getMaxY() - EXTRA_LIL_TRANSLATION));
            }
            entity.setVelocity(new Point2D(entity.velocity.getX(), 0));

            if (entity instanceof WalkingEnemy) {
                ((WalkingEnemy) entity).updatePlatform(this.getBoundsInParent());
            }
        }
    }
}

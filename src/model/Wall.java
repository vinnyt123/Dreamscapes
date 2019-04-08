package model;


import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Wall extends GameObject {

    public Wall(Rectangle rectangle) {
        super();
        this.getChildren().add(rectangle);
    }

    public void intersect(Entity entity) {
        moveX(entity);
        moveY(entity);
    }

    //TODO: Fix corner bug when you jump without moving left or right
    //Move x & y methods automatically do collisions: only move in increments of 1 until hit a wall then stop
    private void moveX(Entity entity) {
        double xDistance = entity.getVelocity().getX();
        boolean movingRight = xDistance > 0;
        for(int i = 0; i < Math.abs(xDistance); i++) {
            if(getBoundsInParent().intersects(this.getBoundsInParent())) {
                if(movingRight) {
                    if(entity.getTranslateX() + this.getBoundsInParent().getWidth() == this.getBoundsInParent().getMinX()) {
                        return;
                    }
                } else {
                    if(entity.getTranslateX() == this.getBoundsInParent().getMaxX()) {
                        return;
                    }
                }
            }
            entity.setTranslateX(entity.getTranslateX() + (movingRight ? 1 : -1));
        }
        entity.setVelocity(new Point2D(0, entity.getVelocity().getY()));
    }

    private void moveY(Entity entity) {
        double yDistance = entity.getVelocity().getY();
        boolean movingDown = yDistance > 0;
        for(int i = 0; i < Math.abs(yDistance); i++) {
            if(getBoundsInParent().intersects(this.getBoundsInParent())) {
                if(movingDown) {
                    if(entity.getTranslateY() + this.getBoundsInParent().getHeight() == this.getBoundsInParent().getMinY()) {
                        if(entity instanceof Player) {
                            ((Player) entity).setInAir(false);
                        }
                        entity.setVelocity(new Point2D(entity.getVelocity().getX(), 0));
                        return;
                    }
                } else {
                    if(entity.getTranslateY() == this.getBoundsInParent().getMaxY()) {
                        entity.setVelocity(new Point2D(entity.getVelocity().getX(), 0));
                        return;
                    }
                }
            } else {
                //Player fell of platform without jumping
                if(entity instanceof Player) {
                    ((Player) entity).setInAir(true);
                }
            }
            entity.setTranslateY(getTranslateY() + (movingDown ? 1 : -1));
        }
    }
}

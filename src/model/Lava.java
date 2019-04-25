package model;

import javafx.scene.shape.Rectangle;

public class Lava extends GameObject {

    private static final double DAMAGE = 0.51;

    Lava(Rectangle rectangle) {
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
            entity.setInAir(true);
            if(entity instanceof Player) {
                ((Player) entity).redFlash();
                ((Player) entity).knockBack(0, -10, true);
            }
            entity.health.setValue(entity.health.getValue() - DAMAGE);
        } else if(entity.getVelocity().getY() > Map.GRAVITY) {
            entity.setInAir(true);
        }
    }
}

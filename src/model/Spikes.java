package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Spikes extends GameObject {

    private static final double DAMAGE = 0.26;
    private static final Image SPIKE_IMAGE = new Image("images/372x455_Spike.png");

    Spikes(Rectangle rectangle) {
        ImageView imageView = new ImageView(SPIKE_IMAGE);
        imageView.setLayoutX(rectangle.getLayoutX());
        imageView.setLayoutY(rectangle.getLayoutY());
        imageView.setFitWidth(rectangle.getWidth());
        imageView.setFitHeight(rectangle.getHeight());
        //Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());

        //newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(imageView);
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
                ((Player) entity).knockBack((entity.getLastMove().getX() < 0) ? 10 : -10, (entity.getLastMove().getY()<0) ? 5 : -5, true);
            } else if( entity instanceof Enemy) {
                ((Enemy) entity).redFlash();
            }
            entity.health.setValue(entity.health.getValue() - DAMAGE);
        } else if(entity.getVelocity().getY() > Map.GRAVITY) {
            entity.setInAir(true);
        }
    }
}

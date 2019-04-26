package model;


import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Wall extends GameObject {

    private Bounds bounds;

    public Wall(Rectangle rectangle) {
        Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());
        newRectangle.setOpacity(1);
        this.getChildren().add(newRectangle);
    }

    Wall(Image image, double layoutX, double layoutY) {
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(layoutX);
        imageView.setLayoutY(layoutY);
        this.getChildren().add(imageView);
    }

    @Override
    public void intersect(Entity entity) {
        if(getBounds().intersects(entity.getBounds())) {
            entity.undoMove();
            moveX(entity);
            moveY(entity);
        } else if(entity.getVelocity().getY() > Map.GRAVITY) {
            entity.setInAir(true);
        }
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public Bounds getBounds() {
        Bounds correctBounds;
        if (this.bounds == null) {
            correctBounds = this.getBoundsInParent();
        } else {
            correctBounds = bounds;
        }
        return correctBounds;
    }
}

package model;

import javafx.scene.shape.Rectangle;

public class Door extends GameObject {


    private String mapToSwitch;

    public Door(String mapToSwitch, Rectangle rectangle) {
        this.mapToSwitch = mapToSwitch;
        Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());
        newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(newRectangle);
    }

    @Override
    public void intersect(Entity entity) {
        if (entity instanceof Player && this.getBoundsInParent().intersects(entity.getBoundsInParent())) {
            ((PlayingState) this.getScene().getRoot().getChildrenUnmodifiable().get(0)).setMap(mapToSwitch);
        }
    }
}

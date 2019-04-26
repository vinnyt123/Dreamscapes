package model;

import javafx.scene.shape.Rectangle;

public class Door extends GameObject {

    private String mapToSwitch;
    private String mapDoorIsOn;

    public Door(String mapToSwitch, Rectangle rectangle, String mapDoorIsOn) {
        this.mapToSwitch = mapToSwitch;
        this.mapDoorIsOn = mapDoorIsOn;
        Rectangle newRectangle = new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight());
        newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(newRectangle);
    }

    @Override
    public void intersect(Entity entity) {
        if (entity instanceof Player && this.getBoundsInParent().intersects(entity.getBounds())) {
            ((PlayingState) this.getScene().getRoot().getChildrenUnmodifiable().get(0)).setMap(mapToSwitch, mapDoorIsOn);
        }
    }
}

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class Door extends GameObject {

    private String mapToSwitch;
    private String mapDoorIsOn;
    private static final Image DOOR_IMAGE = new Image("images/door.png");
    private Line teleportLine;
    private int intersectCount = 0;


    Door(String mapToSwitch, Rectangle rectangle, String mapDoorIsOn) {
        this.mapToSwitch = mapToSwitch;
        this.mapDoorIsOn = mapDoorIsOn;
        ImageView imageView = new ImageView(DOOR_IMAGE);
        imageView.setLayoutX(rectangle.getLayoutX());
        imageView.setLayoutY(rectangle.getLayoutY());
        imageView.setFitWidth(rectangle.getWidth());
        imageView.setFitHeight(rectangle.getHeight());
        teleportLine = new Line(rectangle.getLayoutX() + rectangle.getWidth() / 2, rectangle.getLayoutY(),
                rectangle.getLayoutX() + rectangle.getWidth() / 2, rectangle.getLayoutY() + rectangle.getHeight());
        this.getChildren().add(imageView);
    }

    @Override
    public void intersect(Entity entity) {
        if (entity instanceof Player && teleportLine.getBoundsInParent().intersects(entity.getBounds())) {
            intersectCount++;
            if(intersectCount == 1) {
                ((PlayingState) this.getScene().getRoot().getChildrenUnmodifiable().get(0)).setMap(mapToSwitch, mapDoorIsOn);
            }
        }
    }


}

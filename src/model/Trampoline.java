package model;

import javafx.scene.shape.Rectangle;

public class Trampoline extends GameObject {

    private static double minBounce =  2.0;
    private static double maxBounce = 10.0;

    public Trampoline(Rectangle rectangle) {
        super();
        Rectangle newRectangle = (new Rectangle(rectangle.getLayoutX(), rectangle.getLayoutY(), rectangle.getWidth(), rectangle.getHeight()));
        newRectangle.setFill(rectangle.getFill());
        this.getChildren().add(newRectangle);
    }

    @Override
    public void intersect(Entity entity) {
        if (entity.getBoundsInParent().intersects(this.getBoundsInParent())) {
            double newYvel = 5 * (entity.getVelocity().getY());

            if (newYvel > maxBounce) {
                newYvel = maxBounce;
            } else if (newYvel < minBounce) {
                newYvel = minBounce;
            }
            newYvel *= -1;

            entity.setVelocity(entity.getVelocity().add(0, newYvel));
        }
    }
}

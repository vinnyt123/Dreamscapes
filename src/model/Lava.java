package model;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Lava extends GameObject {

    private static final double DAMAGE = 0.51;
    private final double WIDTH;
    private final double HEIGHT;
    private static final Image LAVA_IMAGE = new Image("images/lava_sheet.png");
    private final int IMAGEWIDTH = 16;
    private SpriteAnimation animation;

    Lava(Rectangle rectangle) {
        WIDTH = rectangle.getWidth();
        HEIGHT = rectangle.getHeight();
        createSprite();
        this.setLayoutX(rectangle.getLayoutX());
        this.setLayoutY(rectangle.getLayoutY());
    }

    private void createSprite() {
        int numberOfImageViews = (int) WIDTH / IMAGEWIDTH;
        //for (int i = 0; i < numberOfImageViews; i++) {
            ImageView imageView = new ImageView(LAVA_IMAGE);
            imageView.setViewport(new Rectangle2D(0, 0, 16, 16));
            imageView.setFitWidth(WIDTH);
            imageView.setFitHeight(HEIGHT);
            this.getChildren().add(imageView);
            animation = new SpriteAnimation(imageView, Duration.millis(4500), 45, 45, 16, 16, 0);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        //}

    }

    @Override
    public void intersect(Entity entity) {
        if(getBoundsInParent().intersects(entity.getBounds())) {
            /*entity.undoMove();
            moveX(entity);
            moveY(entity);*/
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

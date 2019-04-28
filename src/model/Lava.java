package model;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Lava extends GameObject {

    private static final double DAMAGE = 0.51;
    private final double WIDTH;
    private final double HEIGHT;
    private static final double KNOCKBACK = 10/Map.SCALE;
    private static final Image LAVA_IMAGE = new Image("images/lava_32.png");
    private final int IMAGEWIDTH = 32;
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
        Rectangle back = new Rectangle(WIDTH, HEIGHT-30);
        back.setTranslateY(getTranslateY() + 32);
        back.setFill(Paint.valueOf("#c64524"));
        this.getChildren().add(back);
        for (int i = 0; i < numberOfImageViews; i++) {
            ImageView imageView = new ImageView(LAVA_IMAGE);
            imageView.setViewport(new Rectangle2D(0, 0, 32, 32));
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);
            imageView.setTranslateX(i * 32);
            this.getChildren().add(imageView);
            animation = new SpriteAnimation(imageView, Duration.millis(4500), 16, 16, 32, 32, 0);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        }

    }

    @Override
    public void intersect(Entity entity) {
        if(getBoundsInParent().intersects(entity.getBounds())) {
            entity.undoMove();
            moveX(entity);
            moveY(entity);
            entity.setInAir(true);
            entity.health.setValue(entity.health.getValue() - DAMAGE);
            if(entity instanceof Player) {
                if (!entity.isDying) {
                    ((Player) entity).redFlash();
                    ((Player) entity).knockBack(0, -KNOCKBACK, true);
                    if (entity.health.get() <= 0) {
                        entity.setInAir(false);
                    }
                }
            }

        } else if(entity.getVelocity().getY() > Map.GRAVITY) {
            entity.setInAir(true);
        }
    }
}

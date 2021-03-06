package model;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

//Basically followed this tutorial https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
//Need to credit source in documentation?
public class SpriteAnimation extends Transition {

    private ImageView imageView;
    private final int count;
    private final int columns;
    private final int width;
    private final int height;
    private final int offsetY;

    private int lastIndex;

    public ImageView getImageView() {
        return imageView;
    }

    public SpriteAnimation(ImageView imageView, Duration duration, int count, int columns, int width, int height, int offsetY) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.width = width;
        this.height = height;
        this.offsetY = offsetY;
        setCycleDuration(duration);
        this.setCycleCount(1);
        setInterpolator(Interpolator.EASE_BOTH);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x+1, y+1, width-2, height-2));
            lastIndex = index;
        }
    }
}
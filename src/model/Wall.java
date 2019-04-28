package model;


import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;



public class Wall extends GameObject {

    private static final Image GRASSY_PLATFORM = new Image("images/Tutorial_platforms/100x25_GrassyPlatform.png");
    private static final Image GRASSY_CHUNK = new Image("images/Tutorial_platforms/100x66_GrassyChunk.png");
    private static final Image GRASSY_PLATFORM_DARK = new Image("images/Tutorial_platforms/100x25_GrassyPlatformDark.png");
    private static final Image GRASSY_CHUNK_DARK = new Image("images/Tutorial_platforms/100x66_GrassyChunkDark.png");
    private static final Image DIRT_CHUNK = new Image("images/Tutorial_platforms/100x66_DirtChunk.png");

    private final double WIDTH;
    private final double HEIGHT;
    private Bounds bounds;
    private boolean isDark;
    //private ImageView imageView;

    Wall(Rectangle rectangle, boolean isDark) {
        this.isDark = isDark;
        WIDTH = rectangle.getWidth();
        HEIGHT = rectangle.getHeight();
        stitchPlatforms();
        this.setLayoutX(rectangle.getLayoutX());
        this.setLayoutY(rectangle.getLayoutY());
    }

    private void stitchPlatforms() {
        int wholePiecesNeeded = (int) WIDTH / 100;
        int widthOfRemainder = (int) WIDTH % 100;
        int i;
        int j;
        if (HEIGHT <= 25) {
            for (i = 0; i < wholePiecesNeeded; i++) {
                ImageView newImageView = new ImageView((isDark)? GRASSY_PLATFORM_DARK : GRASSY_PLATFORM);
                newImageView.setViewport(new Rectangle2D(0, 0, 100, 25));
                newImageView.setTranslateX(i * 100);
                this.getChildren().add(newImageView);
            }
            if (widthOfRemainder > 0) {
                ImageView lastImageView = new ImageView((isDark)? GRASSY_PLATFORM_DARK : GRASSY_PLATFORM);
                lastImageView.setViewport(new Rectangle2D(0, 0, widthOfRemainder, 25));
                lastImageView.setTranslateX(i * 100);
                this.getChildren().add(lastImageView);
            }
        } else {
            int verticalPiecesNeeded = (int) HEIGHT / 66;
            int verticalRemainder = (int) HEIGHT % 66;

            ImageView imageView;
            for (i = 0; i < wholePiecesNeeded; i++) {
                for (j = 0; j < verticalPiecesNeeded; j++) {
                    if (j == 0) {
                        imageView = new ImageView((isDark)? GRASSY_CHUNK_DARK : GRASSY_CHUNK);
                    } else {
                        imageView = new ImageView(DIRT_CHUNK);
                    }
                    imageView.setViewport(new Rectangle2D(0, 0, 100, 66));
                    imageView.setTranslateX(i * 100);
                    imageView.setTranslateY(j * 66);
                    this.getChildren().add(imageView);
                }
                if (verticalRemainder > 0) {
                    imageView = new ImageView(DIRT_CHUNK);
                    imageView.setViewport(new Rectangle2D(0, 0, 100, verticalRemainder));
                    imageView.setTranslateX(i * 100);
                    imageView.setTranslateY(j * 66);
                    this.getChildren().add(imageView);

                }
            }

            if (widthOfRemainder > 0) {
                for (j = 0; j < verticalPiecesNeeded; j++) {
                    if (j == 0) {
                        imageView = new ImageView((isDark)? GRASSY_CHUNK_DARK : GRASSY_CHUNK);
                    } else {
                        imageView = new ImageView(DIRT_CHUNK);
                    }
                    imageView.setViewport(new Rectangle2D(0, 0, widthOfRemainder, 66));
                    imageView.setTranslateX(i * 100);
                    imageView.setTranslateY(j * 66);
                    this.getChildren().add(imageView);
                }
                if (verticalRemainder > 0) {
                    imageView = new ImageView(DIRT_CHUNK);
                    imageView.setViewport(new Rectangle2D(0, 0, widthOfRemainder, verticalRemainder));
                    imageView.setTranslateX(i * 100);
                    imageView.setTranslateY(j * 66);
                    this.getChildren().add(imageView);
                }
            }
        }

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

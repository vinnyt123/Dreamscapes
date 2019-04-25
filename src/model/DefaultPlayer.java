package model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class DefaultPlayer extends PlayerSprite {

    private ImageView imageViewAttack;
    private static final Image SPRITE_SHEET = new Image("images/sprite_sheet.png");
    private static final Image SPRITE_SHEET_BOOTS = new Image("images/sprite_sheet_boots.png");

    private SpriteAnimation walkRight;
    private SpriteAnimation walkLeft;
    private SpriteAnimation jumpRight;
    private SpriteAnimation jumpLeft;
    private SpriteAnimation damageRight;
    private SpriteAnimation damageLeft;
    private SpriteAnimation standRight;
    private SpriteAnimation standLeft;
    private SpriteAnimation attackRight;
    private SpriteAnimation attackLeft;
    private SpriteAnimation attackSwipeRight;
    private SpriteAnimation attackSwipeLeft;

    DefaultPlayer() {
        super();

        imageView = new ImageView(SPRITE_SHEET);
        imageViewAttack = new ImageView(SPRITE_SHEET);
        imageViewAttack.setViewport(new Rectangle2D(0, 0, 32, 32));
        imageViewAttack.setFitWidth(32);
        imageViewAttack.setFitHeight(48);
        imageViewAttack.setLayoutY(getLayoutY() + Player.HEIGHT/10);
        imageView.setViewport(new Rectangle2D(0, 0, 72, 97));
        imageView.setFitWidth(Player.WIDTH);
        imageView.setFitHeight(Player.HEIGHT);
        //imageView.getTransforms().addAll(new Scale(-1, 1), new Translate(-WIDTH, 0));
        walkRight = new SpriteAnimation(imageView, Duration.millis(100), 11, 11, 72, 97, 0);
        walkLeft = new SpriteAnimation(imageView, Duration.millis(100), 11, 11, 72, 97, 97);
        jumpRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 194);
        jumpLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 291);
        damageRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 388);
        damageLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 485);
        standRight = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 582);
        standLeft = new SpriteAnimation(imageView, Duration.millis(100), 2, 2, 72, 97, 679);
        attackRight = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 97, 776);
        attackLeft = new SpriteAnimation(imageView, Duration.millis(200), 2, 2, 72, 97, 873);
        attackSwipeRight = new SpriteAnimation(imageViewAttack, Duration.millis(200), 4, 4, 32, 32, 970);
        attackSwipeLeft = new SpriteAnimation(imageViewAttack, Duration.millis(200), 4, 4, 32, 32, 1002);
        attackSwipeRight.setOnFinished(e -> this.getChildren().remove(imageViewAttack));
        attackSwipeLeft.setOnFinished(e -> this.getChildren().remove(imageViewAttack));

        imageView.setEffect(colorAdjust);

        /*Reflection reflection = new Reflection();
        imageView.setEffect(reflection);*/

        this.getChildren().add(imageView);

        /*animation = jumpRight;
        animation.getImageView().setEffect(colorAdjust);
        animation.setCycleCount(1);*/
    }

    @Override
    public void walkLeft() {
        walkLeft.play();
    }

    @Override
    public void walkRight() {
        walkRight.play();
    }

    @Override
    public void jumpLeft() {
        jumpLeft.play();
    }

    @Override
    public void jumpRight() {
        jumpRight.play();
    }

    @Override
    public void attackLeft() {
        if (!this.getChildren().contains(imageViewAttack)) {
            this.getChildren().add(imageViewAttack);
            imageViewAttack.setLayoutX(-Player.WIDTH / 2);
        }
        attackSwipeLeft.play();
        attackLeft.play();
    }

    @Override
    public void attackRight() {
        if (!this.getChildren().contains(imageViewAttack)) {
            this.getChildren().add(imageViewAttack);
            imageViewAttack.setLayoutX(Player.WIDTH / 2);
        }
        attackSwipeRight.play();
        attackRight.play();
    }

    @Override
    public void standRight() {
        standRight.play();
    }

    @Override
    public void standLeft() {
        standLeft.play();
    }

    @Override
    public void damageRight() {
        damageRight.play();
    }

    @Override
    public void damageLeft() {
        damageLeft.play();
    }

    @Override
    //Can just change these bounds to slightly smaller than image view bounds to make player appear slightly inside platforms
    public Bounds getBounds() {
        //return new BoundingBox(imageView.getBoundsInParent().getMinX(), imageView.getBoundsInParent().getMinY(), imageView.getBoundsInParent().getWidth(), imageView.getBoundsInParent().getHeight() - 10);
        return imageView.getBoundsInParent();
    }


    @Override
    public void addBoots() {
        imageView.setImage(SPRITE_SHEET_BOOTS);
    }

    @Override
    public void removeBoots() {

    }
}

package model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class DefaultPlayer extends PlayerSprite {

    private static final Image SPRITE_SHEET = new Image("images/sheet.png");
    private static final Image SPRITE_SHEET_BOOTS = new Image("images/sheet_boots.png");

    DefaultPlayer() {
        super();

        imageView = new ImageView(SPRITE_SHEET);
        imageView.setViewport(new Rectangle2D(0, 0, 32, 32));
        imageView.setFitWidth(Player.WIDTH);
        imageView.setFitHeight(Player.HEIGHT);
        standRight = new SpriteAnimation(imageView, Duration.millis(1800), 13, 13, 32, 32, 0);
        standLeft = new SpriteAnimation(imageView, Duration.millis(1800), 13, 13, 32, 32, 257);

        walkRight = new SpriteAnimation(imageView, Duration.millis(200), 8, 8, 32, 32, 32);
        walkLeft = new SpriteAnimation(imageView, Duration.millis(200), 8, 8, 32, 32, 288);

        jumpRight = new SpriteAnimation(imageView, Duration.millis(400), 2, 2, 32, 32, 512);
        jumpLeft = new SpriteAnimation(imageView, Duration.millis(400), 2, 2, 32, 32, 544);

        attackRight = new SpriteAnimation(imageView, Duration.millis(400), 10, 10, 32, 32, 128);
        attackLeft = new SpriteAnimation(imageView, Duration.millis(400), 10, 10, 32, 32, 384);

        damageRight = new SpriteAnimation(imageView, Duration.millis(400), 4, 4, 32, 32, 192);
        damageLeft = new SpriteAnimation(imageView, Duration.millis(400), 4, 4, 32, 32, 448);

        currentAnimation = standRight;

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
        //walkLeft.play();
        currentAnimation = walkLeft;
        //currentAnimation.play();
    }

    @Override
    public void walkRight() {
        //walkRight.play();
        currentAnimation = walkRight;
        //currentAnimation.play();
    }

    @Override
    public void jumpLeft() {
        //jumpLeft.play();
        currentAnimation = jumpLeft;
        //currentAnimation.play();
    }

    @Override
    public void jumpRight() {
        //jumpRight.play();
        currentAnimation = jumpRight;
        //currentAnimation.play();
    }

    @Override
    public void attackLeft() {
        //currentAnimation.stop();
        //stopAll();
        attacking = true;
        currentAnimation = attackLeft;
        currentAnimation.setOnFinished(e -> attacking = false);
        //currentAnimation.play();
    }

    @Override
    public void attackRight() {
        //currentAnimation.stop();
        //stopAll();
        attacking = true;
        currentAnimation = attackRight;
        currentAnimation.setOnFinished(e -> attacking = false);
        //currentAnimation.play();
    }

    @Override
    public void standRight() {
        //standRight.play();
        currentAnimation = standRight;
        //currentAnimation.play();
    }

    @Override
    public void standLeft() {
        //standLeft.play();
        currentAnimation = standLeft;
        //currentAnimation.play();
    }

    @Override
    public void damageRight() {
        damaged = true;
        currentAnimation = damageRight;
        currentAnimation.setOnFinished(e -> damaged = false);
    }

    @Override
    public void damageLeft() {
        damaged = true;
        currentAnimation = damageLeft;
        currentAnimation.setOnFinished(e -> damaged = false);
    }

    @Override
    //Can just change these bounds to slightly smaller than image view bounds to make player appear slightly inside platforms
    public Bounds getBounds() {
        return new BoundingBox(imageView.getBoundsInParent().getMinX() + 20, imageView.getBoundsInParent().getMinY() + 20, imageView.getBoundsInParent().getWidth() - 40, imageView.getBoundsInParent().getHeight() - 25);
        //return imageView.getBoundsInParent();
    }


    @Override
    public void addBoots() {
        imageView.setImage(SPRITE_SHEET_BOOTS);
    }

    @Override
    public void removeBoots() {

    }
}

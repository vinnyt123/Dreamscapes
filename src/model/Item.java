package model;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.PINK;


public abstract class Item extends Group {

    public Item(Image tileImage, Node node) {
        ImageView imageView = new ImageView(tileImage);
        Rectangle tileBorder = new Rectangle(20,20);
        tileBorder.setFill(PINK);
        this.getChildren().addAll(tileBorder, imageView);
        this.setLayoutX(node.getLayoutX());
        this.setLayoutY(node.getLayoutY());
    }

    public abstract boolean intersect(Player player);
}

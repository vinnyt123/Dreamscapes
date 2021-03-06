package model;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;



public abstract class Item extends Group {

    private static int TILE_SIZE = 40;


    Item(Image tileImage, Node node) {
        ImageView imageView = new ImageView(tileImage);
        imageView.setFitHeight(TILE_SIZE - 5);
        imageView.setFitWidth(TILE_SIZE - 5);
        Pane tileBorder = new Pane();
        tileBorder.setPrefWidth(TILE_SIZE);
        tileBorder.setPrefHeight(TILE_SIZE);
        tileBorder.setStyle("-fx-border-style : solid;" +
                "-fx-border-color : CYAN;" +
                "-fx-border-width : 2px;" +
                "-fx-border-radius : 3px;" +
                "-fx-background-color : BLACK;" +
                "-fx-background-radius : 3px;");
        this.getChildren().addAll(tileBorder, imageView);
        imageView.setTranslateX(getTranslateX() + 2.5);
        imageView.setTranslateY(getTranslateY() + 2.5);
        this.setLayoutX(node.getLayoutX());
        this.setLayoutY(node.getLayoutY());
    }

    public abstract boolean intersect(Player player);
}

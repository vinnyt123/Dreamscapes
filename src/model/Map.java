package model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Map extends Pane {

    Player player;
    Parent map;
    private List<Rectangle> walls = new ArrayList<>();

    public Map(Node node, Player player) {
        super(node);
        this.player = player;
        this.getChildren().add(player);
        //Create list of rectangles that are walls/floors
        for(Node item : ((AnchorPane) node).getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                walls.add((Rectangle) item);
            }
        }
    }

    public void moveEntities() {
        player.move();
        checkCollision();
    }

    private void checkCollision() {
        for(Rectangle wall : walls) {
            if(player.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                System.out.println("in wall");
            }
        }
    }
}

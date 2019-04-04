package model;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Map extends Pane {

    private Player player;
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
        player.move(walls);
    }

}

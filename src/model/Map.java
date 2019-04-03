package model;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public class Map extends Pane {

    Player player;

    public Map(Node node, Player player) {
        super(node);
        this.player = player;
        this.getChildren().add(player);
    }

    public void moveEntities() {
        player.move();
    }
}

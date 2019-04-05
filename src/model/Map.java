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
    private final int WIDTH;
    private final int HEIGHT;

    public Map(Node node, Player player, int width, int height) {
        super(node);
        this.player = player;
        this.getChildren().add(player);
        this.WIDTH = width;
        this.HEIGHT = height;
        //Create list of rectangles that are walls/floors
        for(Node item : ((AnchorPane) node).getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                walls.add((Rectangle) item);
            }
        }
    }

    public void moveEntities() {
        player.move(walls);

        //Set layout so player is in middle or not if edge of map (720 & 450 are half of the viewport x & y)
        setLayoutX(720 - player.getTranslateX());
        setLayoutY(450 - player.getTranslateY());
        if(player.getTranslateX() - 720 < 0) {
            setLayoutX(getLayoutX() + (player.getTranslateX() - 720));

        } else if(player.getTranslateX() + 720 > WIDTH) {
            setLayoutX(getLayoutX() + (player.getTranslateX() - (WIDTH - 720)));
        }
        if(player.getTranslateY() + 450 > HEIGHT) {
            setLayoutY(getLayoutY() + (player.getTranslateY() - (HEIGHT - 450)));
        } else if(player.getTranslateY() - 450 < 0) {
            setLayoutY(getLayoutY() + (player.getTranslateY() - 450));
        }
        //Javadoc says relocate is better than setting layout but both work
        //relocate((720 - player.getTranslateX()), (450 - player.getTranslateY()));
        //or
        //setLayoutX(720 - player.getTranslateX());
        //setLayoutY(450 - player.getTranslateY());
    }

}

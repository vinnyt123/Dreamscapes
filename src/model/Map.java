package model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Map extends Pane {

    private Player player;
    private List<Rectangle> walls = new ArrayList<>();
    private List<FlyingEnemy> flyingEnemies = new ArrayList<>();
    private final double WIDTH;
    private final double HEIGHT;

    public Map(Node node, Player player) {
        super(node);
        this.player = player;
        this.getChildren().add(player);
        this.WIDTH = node.getBoundsInParent().getWidth();
        this.HEIGHT = node.getBoundsInParent().getHeight();
        //Create list of rectangles that are walls/floors. Use line start to create enemy spawn point.
        for(Node item : ((AnchorPane) node).getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                walls.add((Rectangle) item);
            } else if (item instanceof Line) {
                if(item.getId().startsWith("flyingEnemy")) {
                    System.out.println(((Line) item).getStartX() + " " + ((Line) item).getStartY());
                    flyingEnemies.add(new FlyingEnemy(((Line) item).getStartX(), ((Line) item).getStartY()));
                    this.getChildren().remove(item);
                }
            }
        }
        this.getChildren().addAll(flyingEnemies);
    }

    public void moveEntities() {
        player.move(walls);
        for(FlyingEnemy enemy : flyingEnemies) {
            enemy.move(walls, new Point2D(player.getTranslateX() + (Player.WIDTH/2), player.getTranslateY() + (Player.HEIGHT/2)));
        }

        //Set layout so player is in middle or not if edge of map (720 & 450 are half of the viewport x & y)
        //TODO: instead of using 720 & 450 get size of stage and use half of those - support resizing - will need
        //to add listener on stage resize property so it updates when resized though
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

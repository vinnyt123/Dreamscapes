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
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<Entity> entities = new ArrayList<Entity>();
    private final double WIDTH;
    private final double HEIGHT;

    public Map(Node node, Player player) {
        super(node);
        this.player = player;
        entities.add(player);
        this.WIDTH = node.getBoundsInParent().getWidth();
        this.HEIGHT = node.getBoundsInParent().getHeight();
        //Create list of rectangles that are walls/floors. Use line start to create enemy spawn point.
        for(Node item : ((AnchorPane) node).getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                gameObjects.add(new Wall((Rectangle) item));
                this.getChildren().remove(item);
                if (item.getId() != null && item.getId().startsWith("enemyPlatform")) {
                    entities.add(new WalkingEnemy(item.getBoundsInParent()));
                }
            } else if (item instanceof Line) {
                if(item.getId().startsWith("flyingEnemy")) {
                    System.out.println(((Line) item).getStartX() + " " + ((Line) item).getStartY());
                    entities.add(new FlyingEnemy(((Line) item).getStartX(), ((Line) item).getStartY(), player));
                    this.getChildren().remove(item);
                }
            }
        }
        this.getChildren().addAll(entities);
        this.getChildren().addAll(gameObjects);
    }

    public void moveEntities() {

        player.move();
        enemies.move();

        for (GameObject gameObject : gameObjects) {
            gameObject.intersect(Player player);
            for (enemy in enemies) {
                gameObject.intersect(enemy);
                enemy.intersect(player);
            }
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

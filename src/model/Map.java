package model;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
        player.move();
        checkPlayerCollision();
    }

    private void checkPlayerCollision() {
        for(Rectangle wall : walls) {
            Bounds playerBounds = player.getBoundsInParent();
            Bounds wallBounds = wall.getBoundsInParent();
            //Check if pairs of player vertices are in the wall to determine which wall it is, then translate accordingly and kill velocity
            if(playerBounds.intersects(wallBounds)) {
                if(wallBounds.contains(playerBounds.getMinX(), playerBounds.getMaxY()) && wallBounds.contains(playerBounds.getMaxX(), playerBounds.getMaxY())) {
                    //bottom
                    player.setInAir(false);
                    player.setVelocity(new Point2D(player.getVelocity().getX(), 0));
                    player.setTranslateY(player.getTranslateY() - (playerBounds.getMaxY() - wallBounds.getMinY()));
                } else if(wallBounds.contains(playerBounds.getMinX(), playerBounds.getMinY()) && wallBounds.contains(playerBounds.getMaxX(), playerBounds.getMinY())) {
                    //top
                    player.setVelocity(new Point2D(player.getVelocity().getX(), 0));
                    player.setTranslateY(player.getTranslateY() - (playerBounds.getMinY() - wallBounds.getMaxY()));
                } else if(wallBounds.contains(playerBounds.getMinX(), playerBounds.getMinY()) && wallBounds.contains(playerBounds.getMinX(), playerBounds.getMaxY())) {
                    //left
                    player.setVelocity(new Point2D(0, player.getVelocity().getY()));
                    player.setTranslateX(player.getTranslateX() - (playerBounds.getMinX() - wallBounds.getMaxX()));
                } else if(wallBounds.contains(playerBounds.getMaxX(), playerBounds.getMinY()) && wallBounds.contains(playerBounds.getMaxX(), playerBounds.getMaxY())) {
                    //right
                    player.setVelocity(new Point2D(0, player.getVelocity().getY()));
                    player.setTranslateX(player.getTranslateX() - (playerBounds.getMaxX() - wallBounds.getMinX()));
                } else {
                    System.out.println("Corner collision big yikes I haven't coded this yet");
                }
            }
        }
    }
}

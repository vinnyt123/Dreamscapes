package model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Map extends Pane {

    private Player player;
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();

    private final double WIDTH;
    private final double HEIGHT;
    static double GRAVITY = 0.4;
    static double TERMINAL_VELOCITY = 15;

    public Map(Pane pane, Player player) {
        super();
        this.player = player;
        this.getChildren().add(player);
        this.WIDTH = pane.getBoundsInParent().getWidth();
        this.HEIGHT = pane.getBoundsInParent().getHeight();
        this.setPrefWidth(pane.getPrefWidth());
        this.setPrefHeight(pane.getPrefHeight());

        //Create list of rectangles that are walls/floors. Use line start to create enemy spawn point.
        //System.out.println(pane.getChildrenUnmodifiable().size());
        for(Node item : pane.getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                gameObjects.add(new Wall((Rectangle) item));
                /*if (item.getId() != null && item.getId().startsWith("enemyPlatform")) {
                    enemies.add(new WalkingEnemy((Rectangle) item));
                }*/
            } else if (item instanceof Line) {
                if(item.getId().startsWith("flyingEnemy")) {
                    //System.out.println(((Line) item).getStartX() + " " + ((Line) item).getStartY());
                    enemies.add(new FlyingEnemy(((Line) item).getStartX(), ((Line) item).getStartY(), player));
                }
            }
        }

        this.getChildren().addAll(enemies);
        this.getChildren().addAll(gameObjects);
    }

    public void moveEntities() {
        //TODO: make this a listener on a simpledouble health
        if(player.health.get() < 0) {
            System.out.println("DEAD");
            ((GameManager) player.getScene().getRoot()).switchToMenu();
        }

        player.move();
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.health.get() <= 0.0) {
                this.getChildren().remove(enemy);
                it.remove();
            }
            enemy.move();
            enemy.intersect(player);
            if(player.isAttacking && player.getCurrentWeapon().getRange() > getDistance(player, enemy) && !enemy.isFlashing) {
                enemy.setKnockBack(true);
                enemy.health.setValue(enemy.health.getValue() - player.getCurrentWeapon().getDamage());
            }
        }
        player.isAttacking = false;

        for (GameObject gameObject : gameObjects) {
            gameObject.intersect(player);
           for (Enemy enemy : enemies) {
               gameObject.intersect(enemy);
           }
        }

        moveCamera();
    }

    private void moveCamera() {
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

    private double getDistance(Player player, Enemy enemy) {
        Point2D playerPos = new Point2D(player.getTranslateX() + Player.WIDTH /2, player.getTranslateY() + Player.HEIGHT /2);
        Point2D enemyPos = new Point2D(enemy.getTranslateX() + enemy.width/2, enemy.getTranslateY() + enemy.width/2);
        return playerPos.distance(enemyPos);
    }
}

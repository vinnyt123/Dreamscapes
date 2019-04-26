package model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Map extends Pane {

    private List<WalkingEnemySpawner> spawners = new ArrayList<>();
    private Player player;
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<ImageView> backgrounds = new ArrayList<>();
    private ImageView foreground = new ImageView();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private ImageView darkness;
    private final double WIDTH;
    private final double HEIGHT;
    static double GRAVITY = 0.4;
    static double TERMINAL_VELOCITY = 15;
    private static final double VIEWPORTWIDTH = 740;
    private static final double VIEWPORTHEIGHT = 480;


    public Map(Pane pane, Player player) {
        super();
        this.player = player;
        this.getChildren().add(player);
        this.WIDTH = pane.getBoundsInParent().getWidth();
        this.HEIGHT = pane.getBoundsInParent().getHeight();
        //this.setPrefWidth(pane.getPrefWidth());
        //this.setPrefHeight(pane.getPrefHeight());

        //Create list of rectangles that are walls/floors. Use line start to create enemy spawn point.
        for(Node item : pane.getChildrenUnmodifiable()) {
            if(item instanceof Rectangle) {
                if (item.getId() != null) {
                    String itemId = item.getId();
                    if (itemId.startsWith("door")) {
                        gameObjects.add(new Door(item.getId().substring(item.getId().indexOf("_") + 1), (Rectangle) item));
                    } else if (itemId.startsWith("doubleJumpBoots")) {
                        items.add(new DoubleJumpBoots(item));
                    } else if (itemId.startsWith("spikes")) {
                        gameObjects.add(new Spikes((Rectangle) item));
                    } else if(itemId.startsWith("lava")) {
                        gameObjects.add(new Lava((Rectangle) item));
                    } else if(itemId.startsWith("enemySpawner")) {
                        WalkingEnemySpawner walkingEnemySpawner;
                        if (itemId.endsWith("Left")) {
                            walkingEnemySpawner = new WalkingEnemySpawner(player, true);
                        } else {
                            walkingEnemySpawner = new WalkingEnemySpawner(player, false);
                        }
                        walkingEnemySpawner.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                        enemies.add(walkingEnemySpawner);
                        spawners.add(walkingEnemySpawner);
                    }
                } else {
                    gameObjects.add(new Wall((Rectangle) item));
                }

            } else if (item instanceof Line) {
                enemies.add(new FlyingEnemy(((Line) item).getStartX(), ((Line) item).getStartY(), player));
            } else if(item instanceof ImageView) {
                if(item.getId().startsWith("darkness")) {
                    darkness = (ImageView) item;
                } else if(item.getId().startsWith("still")) {
                    Image image = ((ImageView) item).getImage();
                    foreground = new ImageView(image);
                    foreground.setFitWidth(WIDTH);
                    foreground.setFitHeight(HEIGHT);
                } else if(item.getId().startsWith("platform")) {
                    gameObjects.add(new Wall(((ImageView) item).getImage(), item.getLayoutX(), item.getLayoutY()));
                } else {
                    Image image = ((ImageView) item).getImage();
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(WIDTH);
                    imageView.setFitHeight(HEIGHT);
                    backgrounds.add(imageView);
                }
            } else if (item instanceof Circle) {
                if (item.getId() != null && item.getId().startsWith("walkingEnemy")) {
                    WalkingEnemy walkingEnemy = new WalkingEnemy(player, null);
                    enemies.add(walkingEnemy);
                    walkingEnemy.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                } else {
                    player.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                }
            }
        }

        this.getChildren().addAll(backgrounds);
        this.getChildren().addAll(enemies);
        this.getChildren().addAll(gameObjects);
        this.getChildren().addAll(items);
        this.getChildren().add(foreground);
        //Remove and re-add player to ensure they're on top of image view
        this.getChildren().remove(player);
        this.getChildren().add(player);
        //this.getChildren().add(darkness);
    }

    void moveEntities() {
        if(player.health.get() <= 0) {
            player.resetPlayer();
            ((GameManager) player.getScene().getRoot()).restartLevel();
            return;
        }
        player.move();
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.isDead) {
                if (enemy instanceof WalkingEnemy) {
                    ((WalkingEnemy) enemy).decrementSpawnerCount();
                }
                enemy.remove();
                this.getChildren().remove(enemy);
                it.remove();
            } else if(enemy.health.get() < 0 && !enemy.isDying) {
                enemy.deadAnimation();
            } else {
                enemy.move();
                enemy.intersect(player);
            }
            if(player.isAttacking && player.getCurrentWeapon().getAttackBounds(player.isRight).intersects(enemy.getBoundsInParent()) && !enemy.isFlashing &&!enemy.isDying) {
                enemy.redFlash();
                enemy.health.setValue(enemy.health.getValue() - player.getCurrentWeapon().getDamage());
                enemy.setKnockBack(true);
            }
        }
        player.isAttacking = false;

        for (GameObject gameObject : gameObjects) {
            gameObject.intersect(player);
           for (Enemy enemy : enemies) {
               gameObject.intersect(enemy);
           }
        }

        for (WalkingEnemySpawner spawner : spawners) {
            WalkingEnemy walkingEnemy = spawner.update();
            if (walkingEnemy != null) {
                enemies.add(walkingEnemy);
                this.getChildren().add(walkingEnemy);
            }
        }

        Iterator<Item> it2 = items.iterator();
        while (it2.hasNext()) {
            Item item = it2.next();
            if (item.intersect(player)) {
                this.getChildren().remove(item);
                it2.remove();
            }
        }

        moveCamera();
    }

    private void moveCamera() {
        //Set layout so player is in middle or not if edge of map (720 & 450 are half of the viewport x & y)
        //TODO: instead of using 720 & 450 get size of stage and use half of those - support resizing - will need
        //to add listener on stage resize property so it updates when resized though
        setLayoutX(VIEWPORTWIDTH - player.getTranslateX());
        setLayoutY(VIEWPORTHEIGHT - player.getTranslateY());
        if(player.getTranslateX() - VIEWPORTWIDTH < 0) {
            setLayoutX(getLayoutX() + (player.getTranslateX() - VIEWPORTWIDTH));

        } else if(player.getTranslateX() + VIEWPORTWIDTH > WIDTH) {
            setLayoutX(getLayoutX() + (player.getTranslateX() - (WIDTH - VIEWPORTWIDTH)));
        }
        if(player.getTranslateY() + VIEWPORTHEIGHT > HEIGHT) {
            setLayoutY(getLayoutY() + (player.getTranslateY() - (HEIGHT - VIEWPORTHEIGHT)));
        } else if(player.getTranslateY() - VIEWPORTHEIGHT < 0) {
            setLayoutY(getLayoutY() + (player.getTranslateY() - VIEWPORTHEIGHT));
        }
        //Javadoc says relocate is better than setting layout but both work
        //relocate((720 - player.getTranslateX()), (450 - player.getTranslateY()));
        //or
        //setLayoutX(720 - player.getTranslateX());
        //setLayoutY(450 - player.getTranslateY());

        scrollBackgrounds();
    }

    private void scrollBackgrounds() {
        for(ImageView imageView : backgrounds) {
            imageView.setTranslateX(player.getTranslateX() * 0.04 * (backgrounds.indexOf(imageView) + 1));
            imageView.setTranslateY(0);

        }
        if (darkness != null) {
            darkness.setLayoutX(player.getTranslateX() - darkness.getBoundsInParent().getWidth() / 2);
            darkness.setLayoutY(player.getTranslateY() - darkness.getBoundsInParent().getHeight() / 2);
        }
    }
}

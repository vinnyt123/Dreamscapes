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
    private List<Enemy> enemies = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private final double WIDTH;
    private final double HEIGHT;
    static final double SCALE = 0.5;
    static double GRAVITY = 0.4/(SCALE * SCALE);
    static double TERMINAL_VELOCITY = 15 / SCALE;
    private static final double VIEWPORTWIDTH = 740;
    private static final double VIEWPORTHEIGHT = 480;
    private String mapFrom;
    private Pane FXMLpane;

    private String mapId;


    public Map(Pane pane, Player player, String mapId, String mapFrom) {
        super();
        this.player = player;
        this.mapId = mapId;
        this.WIDTH = pane.getBoundsInParent().getWidth();
        this.HEIGHT = pane.getBoundsInParent().getHeight();
        this.mapFrom = mapFrom;
        this.FXMLpane = pane;
    }

    public void parseFXML() {
        for(Node item : FXMLpane.getChildrenUnmodifiable()) {
            if (item instanceof Rectangle) {
                if (item.getId() != null) {
                    String itemId = item.getId();
                    if (itemId.startsWith("door")) {
                        gameObjects.add(new Door(item.getId().substring(item.getId().indexOf("_") + 1), (Rectangle) item, mapId));
                    } else if (itemId.startsWith("doubleJumpBoots")) {
                        if (!player.hasDoubleJumpBoots()) {
                            items.add(new DoubleJumpBoots(item));
                        }
                    } else if (itemId.startsWith("spikes")) {
                        gameObjects.add(new Spikes((Rectangle) item));
                    } else if (itemId.startsWith("lava")) {
                        gameObjects.add(new Lava((Rectangle) item));
                    } else if (itemId.startsWith("potion")) {
                        items.add(new HealthPotion(item));
                    } else if (itemId.startsWith("enemySpawner")) {
                        WalkingEnemySpawner walkingEnemySpawner;
                        if (itemId.endsWith("Left")) {
                            if (itemId.contains("__")) {
                                walkingEnemySpawner = new WalkingEnemySpawner(player, true, new Point2D(2400, 300));
                            } else {
                                walkingEnemySpawner = new WalkingEnemySpawner(player, true, new Point2D(2400, 500));
                            }
                        } else {
                            walkingEnemySpawner = new WalkingEnemySpawner(player, false, new Point2D(1500, 500));
                        }
                        walkingEnemySpawner.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                        enemies.add(walkingEnemySpawner);
                        spawners.add(walkingEnemySpawner);
                    }
                } else {
                    gameObjects.add(new Wall((Rectangle) item, mapId.startsWith("Level1")));
                }

            } else if (item instanceof Line) {
                enemies.add(new FlyingEnemy(item.getLayoutX(), item.getLayoutY(), player));
            } else if (item instanceof ImageView) {
                Image image = ((ImageView) item).getImage();
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(WIDTH);
                imageView.setFitHeight(HEIGHT);
                if (!mapId.startsWith("Boss")) {
                    imageView.setTranslateX(-500);
                }
                backgrounds.add(imageView);

            } else if (item instanceof Circle) {
                if (item.getId() != null) {
                    if (item.getId().startsWith("walkingEnemy")) {
                        WalkingEnemy walkingEnemy = new WalkingEnemy(player, null);
                        enemies.add(walkingEnemy);
                        walkingEnemy.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                    } else if (item.getId().startsWith("boss")) {
                        enemies.add(new Boss(player, item.getLayoutX(), item.getLayoutY()));
                    } else if (item.getId().startsWith("playerSpawn")) {
                        if ((mapFrom != null && item.getId().endsWith(mapFrom)) || (mapFrom == null && item.getId().equals("playerSpawn")) || mapId.equals(PlayingState.bossArenaID)) {
                            player.spawnAt(new Point2D(item.getLayoutX(), item.getLayoutY()));
                        }
                    }
                }
            }
        }
            this.getChildren().addAll(backgrounds);
            this.getChildren().addAll(gameObjects);
            this.getChildren().addAll(enemies);
            this.getChildren().addAll(items);
            this.getChildren().add(this.player);
            for (WalkingEnemySpawner spawner : spawners) {
                spawner.setUpCollisions();
            }
    }

    void moveEntities() {
        if(!mapId.startsWith("BossArena")) {
            moveCamera();
        }

        if(player.health.get() <= 0) {
            player.resetPlayer();
        }
        player.move();
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.isDead) {
                if (enemy instanceof WalkingEnemy) {
                    ((WalkingEnemy) enemy).decrementSpawnerCount();
                }
                if (enemy instanceof WalkingEnemySpawner) {
                    spawners.remove(enemy);
                }
                if(!(enemy instanceof Boss)) {
                    enemy.remove();
                    this.getChildren().remove(enemy);
                }
                it.remove();
            } else if(enemy.health.get() < 0 && !enemy.isDying) {
                enemy.deadAnimation();
            } else {
                enemy.move();
                if (!player.isDying) {
                    enemy.intersect(player);
                }
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
    }

    private void moveCamera() {
        //Set layout so player is in middle or not if edge of map (720 & 450 are half of the viewport x & y)
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
            imageView.setTranslateX(player.getTranslateX() * 0.04 * (backgrounds.indexOf(imageView) + 1) - 100);
        }
    }

    String getMapId() {
        return mapId;
    }

    public void bringPlayerForward() {
        this.getChildren().remove(player);
        this.getChildren().add(player);
    }
}

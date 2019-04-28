package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class MainMenuState extends StackPane {

    private static final String BACKGROUND_IMAGE_PATH = "images/MenuBackground.png";
    public static String mainMenuID = "Main Menu";
    public static String mainMenuFile = "view/MainMenu.fxml";
    public static String GameOverID = "Game Complete";
    public static String GameOverFile = "view/GameOverScreen.fxml";

    private FXMLLoader loader;

    private HashMap<String, Node> loadedScreens = new HashMap<>();
    private Pane background;
    private Pane controlsLayer = new Pane();


    public MainMenuState() {
        this.setPrefWidth(Main.SCENE_WIDTH);
        this.setPrefHeight(Main.SCENE_HEIGHT);

        createBackground();
        this.getChildren().addAll(background, controlsLayer);
        loadScreen(mainMenuID, mainMenuFile);
        loadScreen(GameOverID, GameOverFile);
        setScreen(mainMenuID);
        System.out.println(background.getBoundsInParent());
        System.out.println(controlsLayer.getBoundsInParent());
    }

    private void createBackground() {
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_PATH));
        backgroundImage.setFitWidth(Main.SCENE_WIDTH);
        backgroundImage.setFitHeight(Main.SCENE_HEIGHT);
       // backgroundImage.setPreserveRatio(true);
        //backgroundImage.setPickOnBounds(true);
        background = new Pane();
        background.setPrefWidth(Main.SCENE_WIDTH);
        background.setPrefHeight(Main.SCENE_HEIGHT);
        background.setMinHeight(Main.SCENE_HEIGHT);
        background.setMinWidth(Main.SCENE_WIDTH);
        background.setMaxWidth(Main.SCENE_WIDTH);
        background.setMaxHeight(Main.SCENE_HEIGHT);
        background.getChildren().add(backgroundImage);
    }

    public void loadScreen(String screenName, String fxmlPath) {
        loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        try {
            loadedScreens.put(screenName, loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScreen(String name) {
        if (loadedScreens.containsKey(name)) {
            if (!this.getChildren().isEmpty()) {
                controlsLayer.getChildren().clear();
            }
            controlsLayer.getChildren().add(loadedScreens.get(name));
        } else {
            System.out.println("Screen " + name + " has not been loaded.");
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}

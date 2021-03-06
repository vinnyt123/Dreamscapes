package model;

import controllers.ScoresController;
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
    private static String mainMenuFile = "view/MainMenu.fxml";
    public static String scoresId = "Scores";
    public static String scoresPath = "view/Scores.fxml";
    public static String controlsId = "Controls";
    public static String controlsPath = "view/Controls.fxml";
    private final GameManager gameManager;

    private FXMLLoader loader;

    private HashMap<String, Node> loadedScreens = new HashMap<>();
    private Pane background;
    private Pane controlsLayer = new Pane();
    private FXMLLoader controlsLoader;


    MainMenuState(GameManager gameManager) {
        this.setPrefWidth(Main.SCENE_WIDTH);
        this.setPrefHeight(Main.SCENE_HEIGHT);
        this.gameManager = gameManager;

        createBackground();
        this.getChildren().addAll(background, controlsLayer);
        loadScreen(mainMenuID, mainMenuFile);
        loadScreen(scoresId,scoresPath);
        loadScreen(controlsId,controlsPath);
        setScreen(mainMenuID);
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

    private void loadScreen(String screenName, String fxmlPath) {
        loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        try {
            loadedScreens.put(screenName, loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (screenName.equals(scoresId)) {
            controlsLoader = loader;
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
        if (name.equals(scoresId)) {
            ((ScoresController) controlsLoader.getController()).setTableData(gameManager.getHighscores().getHighScores());
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}

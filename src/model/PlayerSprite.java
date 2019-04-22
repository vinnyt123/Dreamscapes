package model;

import javafx.scene.Group;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

public abstract class PlayerSprite extends Group {

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void jump();

    public abstract void attack();
}

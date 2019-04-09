package model;

import javafx.scene.Group;

public abstract class GameObject extends Group {

    public abstract void intersect(Entity entity);
}

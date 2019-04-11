package model;

public class Trampoline extends GameObject {

    @Override
    public void intersect(Entity entity) {
        entity.setVelocity(entity.getVelocity().add(0, -10));
    }
}

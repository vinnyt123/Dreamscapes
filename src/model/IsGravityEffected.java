package model;

public interface IsGravityEffected {

    static final double GRAVITY = 0.4;
    static final double TERMINAL_VELOCITY = 15;
    boolean inAir = true;

    public void applyGravity();
    //I didn't really know what to put in here or if this is shitty design but it means all implementers
    //of this interface fall now, thanks to the entity class.
}

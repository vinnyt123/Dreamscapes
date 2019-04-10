package model;

public class WeaponFists extends Weapon {

    private Player player;
    private static final double RANGE = 50;

    public WeaponFists(Player player) {
        super();
        this.player = player;
        range = RANGE;
    }
}

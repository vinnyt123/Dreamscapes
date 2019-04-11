package model;

public class WeaponFists extends Weapon {

    private Player player;
    private static final double RANGE = 120;
    private static final double DAMAGE = 3;

    public WeaponFists(Player player) {
        super();
        this.player = player;
        range = RANGE;
        damage = DAMAGE;
    }
}

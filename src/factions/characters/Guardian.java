package factions.characters;

import factions.Entity;

public class Guardian extends Entity {
    public static final int BASE_PV = 125;
    public static final int BASE_ATK = 20;
    public static final float BASE_SPEED = 0.8f;

    // TODO: Add more statistics

    public Guardian(String name) {
        super(name, BASE_PV, BASE_ATK, BASE_SPEED);
    }
}

package factions.characters;

import factions.Character;

public class Hunter extends Character {
    public static final int BASE_PV = 75;
    public static final int BASE_ATK = 20;
    public static final float BASE_SPEED = 1.2f;

    // TODO: Add more statistics

    public Hunter(String name) {
        super(name, BASE_PV, BASE_ATK, BASE_SPEED);
    }
}

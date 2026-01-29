package factions.characters;

import factions.Character;

public class Wizard extends Character {
    public static final int BASE_PV = 100;
    public static final int BASE_ATK = 20;
    public static final float BASE_SPEED = 1.0f;

    // TODO: Add more statistics

    public Wizard(String name) {
        super(name, BASE_PV, BASE_ATK, BASE_SPEED);
    }
}

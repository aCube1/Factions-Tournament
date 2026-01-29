package factions;

import factions.characters.Guardian;
import factions.characters.Hunter;
import factions.characters.Wizard;

public class CharacterFactory {

    public static Character createCharacter(CharacterType type, String name) {
        switch (type) {
            case GUARDIAN:
                return new Guardian(name);
            case WIZARD:
                return new Wizard(name);
            case HUNTER:
                return new Hunter(name);
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}

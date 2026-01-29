package factions;

import factions.characters.Guardian;
import factions.characters.Hunter;
import factions.characters.Mage;

public class CharacterFactory {

    public static Character createCharacter(CharacterType type, String name) {
        switch (type) {
            case GUARDIAO:
                return new Guardian(name);
            case MAGO:
                return new Mage(name);
            case CACADOR:
                return new Hunter(name);
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}

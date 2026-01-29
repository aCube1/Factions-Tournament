package factions;
public enum CharacterType {
    GUARDIAN,
    WIZARD,
    HUNTER;

    public static CharacterType fromString(String input) {
        input = input.trim().toLowerCase();
        
        switch (input) {
            case "1":
            case "guardiao":
                return GUARDIAN;
            case "2":
            case "mago":
                return WIZARD;
            case "3":
            case "cacador":
                return HUNTER;
            default:
                return null;
        }
    }
}

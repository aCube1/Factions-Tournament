public enum OptionsCharacter {
    GUARDIAO,
    MAGO,
    CACADOR;

    public static OptionsCharacter fromString(String input) {
        input = input.trim().toLowerCase();
        
        switch (input) {
            case "1":
            case "guardiao":
                return GUARDIAO;
            case "2":
            case "mago":
                return MAGO;
            case "3":
            case "cacador":
                return CACADOR;
            default:
                return null;
        }
    }
}

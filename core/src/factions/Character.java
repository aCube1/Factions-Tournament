package factions;

public abstract class Character {
    private String name = "NAME ME!";
    private int base_pv; // Points de Vie -> 0 = Dead
    private int base_atk; // Attack points
    private float base_speed; // Speed: 1.0 -> Normal; 0.5 -> Half; 2.0 -> Double
    private float current_xp = 0.0f;
    private int current_level = 0;

    public Character(String name, int pv, int atk, float speed) {
        if (!name.isEmpty() || !name.isBlank())
            this.name = name;

        this.base_pv = pv;
        this.base_atk = atk;
        this.base_speed = speed;
    }

    public Character(String name, int base_pv, int base_atk) {
        this(name, base_pv, base_atk, 1.0f);
    }

    public String getName() {
        return name;
    }

    public int getPV() {
        return base_pv;
    }

    public int getAttack() {
        return base_atk;
    }

    public float getSpeed() {
        return base_speed;
    }

    public float getXP() {
        return current_xp;
    }

    public int getLevel() {
        return current_level;
    }

    public void setName(String name) {
        if (name.isEmpty() || name.isBlank())
            return;

        this.name = name;
    }

    public void setPV(int pv) {
        this.base_pv = pv;
    }

    public void setAttack(int attack) {
        this.base_atk = attack;
    }

    public void setSpeed(int speed) {
        this.base_speed = speed;
    }

    public void setXP(float xp) {
        this.current_xp = xp;
    }

    public void setLevel(int level) {
        this.current_level = level;
    }
}

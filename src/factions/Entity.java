package factions;

public abstract class Entity {
    private String _name = "NAME ME!";
    private int _base_pv; // Points de Vie -> 0 = Dead
    private int _base_atk; // Attack points
    private float _base_speed; // Speed: 1.0 -> Normal; 0.5 -> Half; 2.0 -> Double
    private float _current_xp = 0.0f;
    private int _current_level = 0;

    Entity(String name, int pv, int atk, float speed) {
        if (!name.isBlank())
            _name = name;

        _base_pv = pv;
        _base_atk = atk;
        _base_speed = speed;
    }

    Entity(String name, int base_pv, int base_atk) {
        this(name, base_pv, base_atk, 1.0f);
    }

    public String getName() {
        return _name;
    }

    public int getPV() {
        return _base_pv;
    }

    public int getAttack() {
        return _base_atk;
    }

    public float getSpeed() {
        return _base_speed;
    }

    public float getXP() {
        return _current_xp;
    }

    public int getLevel() {
        return _current_level;
    }

    public void setName(String name) {
        if (name.isBlank())
            return;

        _name = name;
    }

    public void setPV(int pv) {
        _base_pv = pv;
    }

    public void setAttack(int attack) {
        _base_atk = attack;
    }

    public void setSpeed(int speed) {
        _base_speed = speed;
    }

    public void setXP(float xp) {
        _current_xp = xp;
    }

    public void setLevel(int level) {
        _current_level = level;
    }

    public void takeDamage(int damage) {
        this._base_pv -= damage;
        if (this._base_pv < 0) {
            this._base_pv = 0;
        }
    }
}

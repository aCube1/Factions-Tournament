package factions.actions;

import factions.Action;
import factions.Arena;
import factions.Entity;

public class AttackAction implements Action {

    private Entity attacker;
    private Entity target;

    public AttackAction(Entity attacker, Entity target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute(Arena arena) {
        if (attacker == null || target == null)
            return;

        if (target.getPV() <= 0)
            return;

        target.takeDamage(attacker.getAttack());
    }

}

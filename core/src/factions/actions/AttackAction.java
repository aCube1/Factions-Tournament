package factions.actions;

import factions.Action;
import factions.Arena;
import factions.Character;

public class AttackAction implements Action {
    
    private Character attacker;
    private Character target;

    public AttackAction(Character attacker, Character target){
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

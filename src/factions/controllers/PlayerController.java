package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.Entity;
import factions.IController;
import factions.actions.AttackAction;

public class PlayerController implements IController {
    private ArrayList<Action> _actions;

    private Entity _controlled_entity;

    public PlayerController(Entity entity) {
        _controlled_entity = entity;
        _actions = new ArrayList<>();
    }

    public Entity getEntity() {
        return _controlled_entity;
    }

    public void queueAttack(Entity target) {
        if (_controlled_entity != null && target != null) {
            _actions.add(new AttackAction(_controlled_entity, target));
        }
    }

    public void queueAction(Action action) {
        if (action != null) {
            _actions.add(action);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public ArrayList<Action> collectActions(Arena arena) {
        var actions = new ArrayList<Action>(_actions);
        _actions.clear();
        return actions;
    }
}

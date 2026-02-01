package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.Entity;
import factions.IController;
import factions.actions.AttackAction;

public class PlayerController implements IController {
    private ArrayList<Action> _actions;
<<<<<<< HEAD
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
=======
    private Entity _entity;

    public PlayerController(Entity entity) {
        this._entity = entity;
        this._actions = new ArrayList<>();
>>>>>>> 532c9b8546609e488baf8011ef8fd0395342981b
    }

    @Override
    public void update() {
<<<<<<< HEAD
        // Player input is handled by the battle scene
=======

>>>>>>> 532c9b8546609e488baf8011ef8fd0395342981b
    }

    @Override
    public ArrayList<Action> collectActions(Arena arena) {
        var actions = new ArrayList<Action>(_actions);
        _actions.clear();
        return actions;
    }
}

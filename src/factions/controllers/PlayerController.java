package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.Entity;
import factions.IController;

public class PlayerController implements IController {
    private ArrayList<Action> _actions;
    private Entity _entity;

    public PlayerController(Entity entity) {
        this._entity = entity;
        this._actions = new ArrayList<>();
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

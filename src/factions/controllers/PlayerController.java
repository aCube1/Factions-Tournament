package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.Entity;
import factions.IController;

public class PlayerController implements IController {
    private Entity player;
    private ArrayList<Action> _actions;

    public PlayerController(Entity player) {
        this.player = player;
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

package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.IController;

public class PlayerController implements IController {
    ArrayList<Action> _actions;

    public PlayerController() {
        _actions = new ArrayList<>();
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

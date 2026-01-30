package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.Arena;
import factions.IController;
import factions.Entity;

public class AIController implements IController {
    private ArrayList<Action> _actions;
    private Entity aiEntity;

    public AIController(Entity aiEntity) {
        this.aiEntity = aiEntity;
        _actions = new ArrayList<Action>();
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

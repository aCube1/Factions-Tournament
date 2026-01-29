package factions.controllers;

import java.util.ArrayList;

import factions.Action;
import factions.IController;

public class AIController implements IController {
    ArrayList<Action> _actions;

    AIController() {

    }

    @Override
    public void update() {
    }

    @Override
    public ArrayList<Action> collectActions() {
        var actions = new ArrayList<Action>(_actions);
        _actions.clear();

        return actions;
    }
}

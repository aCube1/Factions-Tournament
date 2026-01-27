package factions.controllers;

import com.badlogic.gdx.utils.Array;

import factions.Action;
import factions.IController;
import factions.InputManager;

public class AIController implements IController {
    Array<Action> _actions;

    public AIController() {

    }

    @Override
    public void update(InputManager input) {
    }

    @Override
    public Array<Action> collectActions() {
        var actions = new Array<Action>(_actions);
        _actions.clear();

        return actions;
    }
}

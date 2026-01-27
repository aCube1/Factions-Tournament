package factions.controllers;

import com.badlogic.gdx.utils.Array;

import factions.Action;
import factions.IController;
import factions.InputManager;

public class PlayerController implements IController {
    Array<Action> _actions;

    public PlayerController() {
        _actions = new Array<>();
    }

    @Override
    public Array<Action> collectActions() {
        var actions = new Array<Action>(_actions);
        _actions.clear();

        return actions;
    }

    @Override

    public void update(InputManager input) {
    }
}

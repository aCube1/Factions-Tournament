package factions;

import com.badlogic.gdx.utils.Array;

public interface IController {
    public void update(InputManager input);

    public Array<Action> collectActions();
}

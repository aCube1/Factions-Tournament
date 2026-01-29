package factions;

import java.util.ArrayList;

public interface IController {
    public void update();

    public ArrayList<Action> collectActions(Arena arena);
}

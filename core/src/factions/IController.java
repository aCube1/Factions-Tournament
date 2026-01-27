package factions;

import java.util.List;
import factions.Action;

public interface IController {

    List<Action> collectActions(Arena arena);

}

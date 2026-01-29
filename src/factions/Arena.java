package factions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Arena {
    public static final int TEAM_NONE = 0;
    public static final int TEAM_BLUE = 1 << 0;
    public static final int TEAM_RED = 1 << 1;
    public static final int TEAM_ALL = TEAM_BLUE | TEAM_RED;

    private ArrayList<Entity> _blue_team;
    private ArrayList<Entity> _red_team;
    private int _winner = TEAM_NONE;

    Arena() {
        _blue_team = new ArrayList<>();
        _red_team = new ArrayList<>();
    }

    public void computeTurn(IController controller) {
        if (_winner != TEAM_NONE)
            return; // Some team already won, there's nothing to do here

        ArrayList<Action> actions = controller.collectActions();
        for (Action action : actions) { // Execute actions
            action.execute(this);
        }

        // Remove dead characters
        _blue_team.removeIf(character -> character.getPV() == 0);
        _red_team.removeIf(character -> character.getPV() == 0);

        // Can both teams lose at the same time?
        _winner = TEAM_NONE;

        if (_blue_team.isEmpty()) {
            _winner |= TEAM_RED;
        }
        if (_red_team.isEmpty()) {
            _winner |= TEAM_BLUE;
        }
    }

    public void addCharacter(int team_color, Entity character) {
        var team = getTeam(team_color);
        team.ifPresent(self -> self.add(character));
    }

    public Optional<List<Entity>> getTeam(int team_color) {
        switch (team_color) {
            case TEAM_BLUE:
                return Optional.of(_blue_team);
            case TEAM_RED:
                return Optional.of(_red_team);
        }

        return Optional.empty();
    }

    public int getWinner() {
        return _winner;
    }

}

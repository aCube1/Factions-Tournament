package factions;

import java.util.*;
import factions.Character;

public class Arena {
    public static final int TEAM_NONE = 0;
    public static final int TEAM_BLUE = 1 << 0;
    public static final int TEAM_RED = 1 << 1;
    public static final int TEAM_ALL = TEAM_BLUE | TEAM_RED;

    private ArrayList<Character> _blueTeam;
    private ArrayList<Character> _redTeam;
    private int _winner = TEAM_NONE;

    public Arena() {
        _blueTeam = new ArrayList<Character>();
        _redTeam = new ArrayList<Character>();
    }

    public void computeTurn(IController controller) {
        if (_winner != TEAM_NONE)
            return; // Some team already won, there's nothing to do here

        // Remove dead characters
        _blueTeam.removeIf(character -> character.getPV() == 0);
        _redTeam.removeIf(character -> character.getPV() == 0);

        // Can both teams lose at the same time?
        _winner = TEAM_NONE;
        if (_blueTeam.isEmpty()) {
            _winner |= TEAM_RED;
        }
        if (_redTeam.isEmpty()) {
            _winner |= TEAM_BLUE;
        }
    }

    public void addCharacter(int team_color, Character character) {
        var team = getTeam(team_color);
        team.ifPresent(self -> self.add(character));
    }

    public Optional<List<Character>> getTeam(int team_color) {
        switch (team_color) {
            case TEAM_BLUE:
                return Optional.of(_blueTeam);
            case TEAM_RED:
                return Optional.of(_redTeam);
        }

        return Optional.empty();
    }

    public int getWinner() {
        return _winner;
    }

}

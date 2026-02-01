package factions.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import factions.Action;
import factions.Arena;
import factions.Entity;
import factions.IController;
import factions.actions.AttackAction;

public class AIController implements IController {
    private ArrayList<Action> _actions;
<<<<<<< HEAD
    private Entity _controlled_entity;
    private Random _random;

    public AIController(Entity entity) {
        _controlled_entity = entity;
        _actions = new ArrayList<>();
        _random = new Random();
    }

    public Entity getEntity() {
        return _controlled_entity;
=======
    private Entity _entity;

    public AIController(Entity entity) {
        this._entity = entity;
        _actions = new ArrayList<Action>();
>>>>>>> 532c9b8546609e488baf8011ef8fd0395342981b
    }

    @Override
    public void update() {
        // AI logic can be expanded here
    }

    @Override
    public ArrayList<Action> collectActions(Arena arena) {
        // Simple AI: Attack a random enemy from the opposing team
        if (_controlled_entity != null && _controlled_entity.getPV() > 0) {
            // Determine which team this AI is on
            boolean isBlueTeam = arena.getTeam(Arena.TEAM_BLUE)
                .map(team -> team.contains(_controlled_entity))
                .orElse(false);

            // Get opposing team
            int opposingTeam = isBlueTeam ? Arena.TEAM_RED : Arena.TEAM_BLUE;

            arena.getTeam(opposingTeam).ifPresent(enemies -> {
                if (!enemies.isEmpty()) {
                    // Pick a random alive enemy
                    List<Entity> aliveEnemies = new ArrayList<>();
                    for (Entity enemy : enemies) {
                        if (enemy.getPV() > 0) {
                            aliveEnemies.add(enemy);
                        }
                    }

                    if (!aliveEnemies.isEmpty()) {
                        Entity target = aliveEnemies.get(_random.nextInt(aliveEnemies.size()));
                        _actions.add(new AttackAction(_controlled_entity, target));
                    }
                }
            });
        }

        var actions = new ArrayList<Action>(_actions);
        _actions.clear();
        return actions;
    }
}

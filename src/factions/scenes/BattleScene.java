package factions.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import factions.Arena;
import factions.Entity;
import factions.IScene;
import factions.SceneManager;
import factions.controllers.PlayerController;
import factions.controllers.AIController;

public class BattleScene implements IScene {
    private Screen _screen;
    private SceneManager _scene_manager;
    private Arena _arena;

    private PlayerController _player;
    private AIController _ai;

    private List<String> _battle_log;
    private int _current_turn;
    private BattleState _state;

    private int _selected_target;

    private static final int MAX_LOG_LINES = 10;

    private enum BattleState {
        PLAYER_TURN,
        ENEMY_TURN,
        ANIMATING,
        VICTORY,
        DEFEAT
    }

    public BattleScene(Screen screen, Arena arena, PlayerController player, AIController ai) {
        _screen = screen;
        _arena = arena;
        _player = player;
        _ai = ai;
        _battle_log = new ArrayList<>();
        _current_turn = 1;
        _state = BattleState.PLAYER_TURN;
        _selected_target = 0;

        addLog("Batalha iniciada!");
        addLog("Turno do jogador!");
    }

    @Override
    public void onEnter() {
        _current_turn = 1;
        _state = BattleState.PLAYER_TURN;
        _battle_log.clear();
        addLog("Batalha iniciada!");
    }

    @Override
    public void onExit() {
    }

    @Override
    public void setSceneManager(SceneManager manager) {
        _scene_manager = manager;
    }

    @Override
    public void update() {
        try {
            KeyStroke key = _screen.pollInput();

            if (key != null) {
                handleInput(key);
            }

            // Check win/loss conditions
            checkBattleEnd();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleInput(KeyStroke key) {
        if (_state == BattleState.PLAYER_TURN) {
            if (key.getKeyType() == KeyType.ArrowUp) {
                _selected_target = Math.max(0, _selected_target - 1);
            } else if (key.getKeyType() == KeyType.ArrowDown) {
                int maxTargets = _arena.getTeam(Arena.TEAM_RED)
                    .map(team -> team.size())
                    .orElse(0);
                _selected_target = Math.min(maxTargets - 1, _selected_target + 1);
            } else if (key.getKeyType() == KeyType.Enter) {
                executePlayerTurn();
            } else if (key.getKeyType() == KeyType.Escape) {
                if (_scene_manager != null) {
                    _scene_manager.switchScene("main_menu");
                }
            }
        } else if (_state == BattleState.VICTORY || _state == BattleState.DEFEAT) {
            if (key.getKeyType() == KeyType.Enter || key.getKeyType() == KeyType.Escape) {
                if (_scene_manager != null) {
                    _scene_manager.switchScene("main_menu");
                }
            }
        }
    }

    private void executePlayerTurn() {
        _arena.getTeam(Arena.TEAM_RED).ifPresent(enemies -> {
            if (_selected_target >= 0 && _selected_target < enemies.size()) {
                Entity target = enemies.get(_selected_target);
                if (target.getPV() > 0) {
                    int damage = _player.getEntity().getAttack();
                    addLog(_player.getEntity().getName() + " atacou " + target.getName() + " causando " + damage + " de dano!");

                    _player.queueAttack(target);
                    _state = BattleState.ENEMY_TURN;

                    // Execute player action
                    _arena.computeTurn(List.of(_player));

                    // Execute AI turn
                    executeAITurn();
                }
            }
        });
    }

    private void executeAITurn() {
        Entity aiEntity = _ai.getEntity();
        if (aiEntity.getPV() > 0) {
            _arena.computeTurn(List.of(_ai));

            // Log AI action
            addLog(aiEntity.getName() + " atacou!");

            _current_turn++;
            _state = BattleState.PLAYER_TURN;
            addLog("--- Turno " + _current_turn + " ---");
            addLog("Sua vez!");
        }
    }

    private void checkBattleEnd() {
        int winner = _arena.getWinner();

        if (winner == Arena.TEAM_BLUE) {
            _state = BattleState.VICTORY;
            addLog("=== VITÓRIA! ===");
        } else if (winner == Arena.TEAM_RED) {
            _state = BattleState.DEFEAT;
            addLog("=== DERROTA! ===");
        }
    }

    private void addLog(String message) {
        _battle_log.add(message);
        if (_battle_log.size() > MAX_LOG_LINES) {
            _battle_log.remove(0);
        }
    }

    @Override
    public void render() throws IOException {
        _screen.clear();
        TextGraphics graphics = _screen.newTextGraphics();
        TerminalSize size = _screen.getTerminalSize();

        // Draw title
        drawCenteredText(graphics, 1, "=== BATALHA ===", TextColor.ANSI.YELLOW);

        // Draw player team (blue)
        drawTeam(graphics, 3, "SUA EQUIPE", Arena.TEAM_BLUE, TextColor.ANSI.CYAN);

        // Draw enemy team (red)
        drawTeam(graphics, 12, "EQUIPE INIMIGA", Arena.TEAM_RED, TextColor.ANSI.RED);

        // Draw battle log
        drawBattleLog(graphics, size.getRows() - MAX_LOG_LINES - 3);

        // Draw controls
        drawControls(graphics, size.getRows() - 2);

        _screen.refresh();
    }

    private void drawTeam(TextGraphics graphics, int startY, String title, int teamColor, TextColor color) {
        graphics.setForegroundColor(color);
        graphics.putString(5, startY, title);

        _arena.getTeam(teamColor).ifPresent(team -> {
            int y = startY + 1;
            int index = 0;
            for (Entity entity : team) {
                String prefix = "";

                // Highlight selected target
                if (teamColor == Arena.TEAM_RED && index == _selected_target && _state == BattleState.PLAYER_TURN) {
                    prefix = "> ";
                    graphics.setForegroundColor(TextColor.ANSI.YELLOW);
                } else {
                    prefix = "  ";
                    graphics.setForegroundColor(color);
                }

                String status = entity.getPV() > 0 ? "VIVO" : "MORTO";
                String line = String.format("%s%s - PV: %d/%d - ATK: %d - [%s]",
                    prefix,
                    entity.getName(),
                    entity.getPV(),
                    getMaxPV(entity),
                    entity.getAttack(),
                    status);

                graphics.putString(7, y, line);
                y++;
                index++;
            }
        });
    }

    private int getMaxPV(Entity entity) {
        // This should ideally be stored in Entity, but for now we estimate based on class name
        String className = entity.getClass().getSimpleName();
        switch (className) {
            case "Guardian": return 125;
            case "Wizard": return 100;
            case "Hunter": return 75;
            default: return 100;
        }
    }

    private void drawBattleLog(TextGraphics graphics, int startY) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(5, startY, "--- LOG DE BATALHA ---");

        int y = startY + 1;
        for (String log : _battle_log) {
            graphics.putString(5, y, log);
            y++;
        }
    }

    private void drawControls(TextGraphics graphics, int y) {
        graphics.setForegroundColor(TextColor.ANSI.GREEN);

        if (_state == BattleState.PLAYER_TURN) {
            graphics.putString(5, y, "↑/↓: Selecionar alvo | ENTER: Atacar | ESC: Menu");
        } else if (_state == BattleState.VICTORY || _state == BattleState.DEFEAT) {
            graphics.putString(5, y, "ENTER ou ESC: Voltar ao menu");
        } else {
            graphics.putString(5, y, "Aguarde...");
        }
    }

    private void drawCenteredText(TextGraphics graphics, int y, String text, TextColor color) {
        TerminalSize size = _screen.getTerminalSize();
        int x = (size.getColumns() - text.length()) / 2;
        graphics.setForegroundColor(color);
        graphics.putString(x, y, text);
    }
}

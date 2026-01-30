package factions;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

import factions.controllers.AIController;
import factions.controllers.PlayerController;
import factions.EntityFactory;
import factions.scenes.CharacterSelectionScene;
import factions.scenes.MainMenuScene;

public class Game implements WindowListener {
    public static final int DEFAULT_WIDTH = 960;
    public static final int DEFAULT_HEIGHT = 540;
    public static final int TARGET_FPS = 30;
    public static final long TARGET_FRAMETIME = 1_000_000_000L / TARGET_FPS; // NS per frame

    private static final TextColor BG_DARK = new TextColor.RGB(15, 15, 20);
    private static final TextColor BG_GUI = new TextColor.RGB(20, 20, 25);

    private static final TextColor FG_LIGHT = new TextColor.RGB(220, 220, 230);
    private static final TextColor FG_EDITABLE = new TextColor.RGB(200, 200, 210);
    private static final TextColor FG_SELECTED = new TextColor.RGB(15, 15, 20);

    private static final TextColor BG_EDITABLE = new TextColor.RGB(25, 25, 35);
    private static final TextColor BG_SELECTED = new TextColor.RGB(255, 200, 50);

    private static Game _intance;

    private final Screen _screen;
    private final SceneManager _scene_manager;
    private boolean _window_should_close;

    private Arena _arena;
    private PlayerController _player;
    private AIController _main_ai;
    private List<IController> _controllers; 

    // FPS syncronization and update
    private long _last_frame_time;
    private long _fps_timer;
    private long _frame_count;
    private int _current_fps;

    public static Game getInstance() {
        return _intance;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        LanternaThemes.registerTheme("darkone", SimpleTheme.makeTheme(
                false,
                FG_LIGHT,
                BG_DARK,
                FG_EDITABLE,
                BG_EDITABLE,
                FG_SELECTED,
                BG_SELECTED,
                BG_GUI));

        Screen screen = null;

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        SwingTerminal terminal = new SwingTerminal();

        JFrame frame = new JFrame("Factions Tournament");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFont(font);
        frame.add(terminal);

        frame.pack();
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocationRelativeTo(null); // Center terminal on screen
        frame.setVisible(true);
        frame.setFocusable(true);

        terminal.requestFocusInWindow();

        try {
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            Game game = new Game(terminal, screen);
            frame.addWindowListener(game);

            while (!game._window_should_close) {
                game.doFrame();
                game.syncFPS();

                frame.setTitle("Factions Tournament - FPS: " + game.getCurrentFPS());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null)
                screen.stopScreen();

            terminal.close();
            frame.dispose();
        }
    }

    private Game(SwingTerminal terminal, Screen screen) {
        _screen = screen;
        _scene_manager = new SceneManager();
        _scene_manager.addScene("main_menu", new MainMenuScene(screen));
        _scene_manager.addScene("character_select", new CharacterSelectionScene(screen));
        _scene_manager.switchScene("main_menu");

        _window_should_close = false;

        _arena = new Arena();
        _controllers = new ArrayList<>();

        Entity player = EntityFactory.createCharacter(CharacterType.GUARDIAN, "Jogador");
      
    
        Entity aiEntity = EntityFactory.createCharacter(CharacterType.HUNTER, "CPU");
       

        _player = new PlayerController(player);
        _main_ai = new AIController(aiEntity);

        _controllers.add(_player);
        _controllers.add(_main_ai);


        _last_frame_time = System.nanoTime();
        _fps_timer = _last_frame_time;
        _frame_count = 0;
        _current_fps = 0;

        _intance = this;
    }

    private void doFrame() throws IOException {
        String current_scene = _scene_manager.getCurrentSceneName();
        if (current_scene != null && current_scene.equals("exit")) {
            _window_should_close = true;
            return;
        }

        _player.update();
        _main_ai.update();

        Entity playerEntity = EntityFactory.createCharacter(
        CharacterType.GUARDIAN, "Jogador");

        Entity aiEntity = EntityFactory.createCharacter(
        CharacterType.HUNTER, "CPU");

        _arena.addCharacter(Arena.TEAM_BLUE, playerEntity);
        _arena.addCharacter(Arena.TEAM_RED, aiEntity);

        _arena.computeTurn(_controllers);

        _scene_manager.update();
        _scene_manager.render();
        _screen.doResizeIfNecessary();
    }

    private void syncFPS() {
        long current_time = System.nanoTime();
        long delta_time = current_time - _last_frame_time;
        long sleep_time = TARGET_FRAMETIME - delta_time;

        if (sleep_time > 0) {
            try {
                long millis = sleep_time / 1_000_000; // NS -> MS
                int nanos = ((int) sleep_time) % 1_000_000; // Just to make sure it's NS
                Thread.sleep(millis, nanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        _last_frame_time = System.nanoTime();

        // Update FPS counter
        _frame_count++;
        current_time = System.nanoTime();
        delta_time = current_time - _fps_timer;

        // Only update after 1 second
        if (delta_time >= 1_000_000_000L) {
            _current_fps = (int) _frame_count;
            _frame_count = 0;
            _fps_timer = current_time;
        }
    }

    private CharacterType askCharacterType(Screen screen) throws IOException {
        TextGraphics gfx = screen.newTextGraphics();

        while (true) {
            screen.clear();

            gfx.putString(0, 2, "Escolha seu personagem:");
            gfx.putString(0, 4, "1 - Guardião");
            gfx.putString(0, 5, "2 - Mago");
            gfx.putString(0, 6, "3 - Caçador");
            gfx.putString(0, 8, "Digite sua escolha:");

            screen.refresh();

            KeyStroke key = screen.readInput();

            if (key.getKeyType() == KeyType.Character) {
                CharacterType type = CharacterType.fromString(
                        String.valueOf(key.getCharacter()));

                if (type != null) {
                    return type;
                }
            }
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        _window_should_close = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    public int getCurrentFPS() {
        return _current_fps;
    }
}

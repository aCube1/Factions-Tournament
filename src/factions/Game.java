package factions;

import factions.controllers.*;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class Game implements WindowListener {
    static final int DEFAULT_WIDTH = 800;
    static final int DEFAULT_HEIGHT = 600;
    static final int TARGET_FPS = 30;
    static final long TARGET_FRAMETIME = 1_000_000_000L / TARGET_FPS; // NS per frame

    private boolean _window_should_close;
    private InputManager _input;

    private Arena _arena;
    private PlayerController _player;
    private AIController _main_ai;
    private List<IController> _controllers;

    // FPS syncronization and update
    private long _last_frame_time;
    private long _fps_timer;
    private long _frame_count;
    private int _current_fps;

    TextColor _color;

    public static void main(String[] args) throws IOException, InterruptedException {
        Screen screen = null;

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        SwingTerminal terminal = new SwingTerminal();

        JFrame frame = new JFrame("Factions Tournament");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFont(font);
        frame.add(terminal);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center terminal on screen
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        try {
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            Game game = new Game(terminal);
            frame.addWindowListener(game);

            while (!game._window_should_close) {
                game.doFrame(screen);
                game.syncFPS();
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

    private Game(SwingTerminal terminal) {
        _window_should_close = false;
        _input = new InputManager(terminal);

        _arena = new Arena();
        _player = new PlayerController();
        _main_ai = new AIController();
        _controllers = new ArrayList<>();

        _last_frame_time = System.nanoTime();
        _fps_timer = _last_frame_time;
        _frame_count = 0;
        _current_fps = 0;

        _color = TextColor.ANSI.BLUE;
    }

    private void doFrame(Screen screen) throws IOException {
        // TODO: Check both player's and AI's commands
        // and compute current turn

        _player.update();
        _main_ai.update();

        _arena.computeTurn(_controllers);

        _input.pollInput();
        screen.clear();

        TextGraphics gfx = screen.newTextGraphics();

        if (_input.isKeyJustPressed(KeyEvent.VK_TAB)) {
            if (_color == TextColor.ANSI.BLUE)
                _color = TextColor.ANSI.GREEN;
            else
                _color = TextColor.ANSI.BLUE;
        }

        String fps_text = String.format("FPS: %d", _current_fps);
        gfx.setBackgroundColor(_color);
        gfx.putString(0, 0, fps_text);

        screen.refresh();

        screen.doResizeIfNecessary();
    }

    private void syncFPS() {
        long current_time = System.nanoTime();
        long elapsed_time = current_time - _last_frame_time;
        long sleep_time = TARGET_FRAMETIME - elapsed_time;

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
        elapsed_time = current_time - _fps_timer;

        // Only update after 1 second
        if (elapsed_time >= 1_000_000_000L) {
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

}

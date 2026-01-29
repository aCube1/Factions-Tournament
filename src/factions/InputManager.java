package factions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.Character;

import com.googlecode.lanterna.terminal.swing.SwingTerminal;

// Written with help of Github Copilot. It literally just helped debugging
public class InputManager implements KeyListener {
    private final SwingTerminal _terminal;

    private final Set<Integer> _keys_down;
    private final Set<Character> _chars_down;

    private final Set<Integer> _previous_keys_down;
    private final Set<Character> _previous_chars_down;

    // Why TF Java does not have a simple Dynamic Queue?
    private final ConcurrentLinkedQueue<KeyEvent> _key_press_queue;
    private final ConcurrentLinkedQueue<KeyEvent> _key_release_queue;

    public InputManager(SwingTerminal terminal) {
        _terminal = terminal;
        _keys_down = new HashSet<>();
        _chars_down = new HashSet<>();
        _previous_keys_down = new HashSet<>();
        _previous_chars_down = new HashSet<>();
        _key_press_queue = new ConcurrentLinkedQueue<>();
        _key_release_queue = new ConcurrentLinkedQueue<>();

        _terminal.addKeyListener(this);
    }

    public void pollInput() {
        _previous_keys_down.clear();
        _previous_keys_down.addAll(_keys_down);
        _previous_chars_down.clear();
        _previous_chars_down.addAll(_chars_down);

        _keys_down.clear();
        _chars_down.clear();

        while (!_key_press_queue.isEmpty()) {
            KeyEvent event = _key_press_queue.poll();
            _keys_down.add(event.getKeyCode());
            if (event.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                _chars_down.add(event.getKeyChar());
            }
        }

        while (!_key_release_queue.isEmpty()) {
            KeyEvent event = _key_release_queue.poll();
            _keys_down.remove(event.getKeyCode());
            if (event.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                _chars_down.remove(event.getKeyChar());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        _key_press_queue.offer(event);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        _key_release_queue.offer(event);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // NO!
    }

    public boolean isKeyDown(int keycode) {
        return _keys_down.contains(keycode);
    }

    public boolean isKeyDown(char key) {
        return _chars_down.contains(key)
                || _chars_down.contains(Character.toLowerCase(key))
                || _chars_down.contains(Character.toUpperCase(key));
    }

    public boolean isKeyJustPressed(int keycode) {
        return _keys_down.contains(keycode) && !_previous_keys_down.contains(keycode);
    }

    public boolean isKeyJustPressed(char key) {
        char low = Character.toLowerCase(key);
        char up = Character.toUpperCase(key);
        boolean is_down = _chars_down.contains(key)
                || _chars_down.contains(low)
                || _chars_down.contains(up);
        boolean was_down = _previous_chars_down.contains(key)
                || _previous_chars_down.contains(key)
                || _previous_chars_down.contains(key);
        return is_down && !was_down;
    }

    public boolean isKeyJustReleased(int keycode) {
        return !_keys_down.contains(keycode) && _previous_keys_down.contains(keycode);
    }

    public boolean isKeyJustJustReleased(char key) {
        char low = Character.toLowerCase(key);
        char up = Character.toUpperCase(key);
        boolean is_down = _chars_down.contains(key)
                || _chars_down.contains(low)
                || _chars_down.contains(up);
        boolean was_down = _previous_chars_down.contains(key)
                || _previous_chars_down.contains(key)
                || _previous_chars_down.contains(key);
        return !is_down && was_down;
    }

    // Helpers

    public boolean isUpPressed() {
        return isKeyDown(KeyEvent.VK_UP) || isKeyDown('w') || isKeyDown('W');
    }

    public boolean isDownPressed() {
        return isKeyDown(KeyEvent.VK_DOWN) || isKeyDown('s') || isKeyDown('S');
    }

    public boolean isLeftPressed() {
        return isKeyDown(KeyEvent.VK_LEFT) || isKeyDown('a') || isKeyDown('A');
    }

    public boolean isRightPressed() {
        return isKeyDown(KeyEvent.VK_RIGHT) || isKeyDown('d') || isKeyDown('D');
    }

    public boolean isUpJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_UP) || isKeyJustPressed('w') || isKeyJustPressed('W');
    }

    public boolean isDownJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_DOWN) || isKeyJustPressed('s') || isKeyJustPressed('S');
    }

    public boolean isLeftJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_LEFT) || isKeyJustPressed('a') || isKeyJustPressed('A');
    }

    public boolean isRightJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_RIGHT) || isKeyJustPressed('d') || isKeyJustPressed('D');
    }

    public boolean isUpJustReleased() {
        return isKeyJustReleased(KeyEvent.VK_UP) || isKeyJustReleased('w') || isKeyJustReleased('W');
    }

    public boolean isDownJustReleased() {
        return isKeyJustReleased(KeyEvent.VK_DOWN) || isKeyJustReleased('s') || isKeyJustReleased('S');
    }

    public boolean isLeftJustReleased() {
        return isKeyJustReleased(KeyEvent.VK_LEFT) || isKeyJustReleased('a') || isKeyJustReleased('A');
    }

    public boolean isRightJustReleased() {
        return isKeyJustReleased(KeyEvent.VK_RIGHT) || isKeyJustReleased('d') || isKeyJustReleased('D');
    }

}

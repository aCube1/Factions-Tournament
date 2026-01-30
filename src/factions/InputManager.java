package factions;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InputManager {
    private final Screen _screen;

    private final Set<Character> _keys_down;
    private final Set<KeyType> _special_keys_down;

    private final Set<Character> _previous_keys_down;
    private final Set<KeyType> _previous_special_keys_down;

    public InputManager(Screen screen) {
        _screen = screen;
        _keys_down = new HashSet<>();
        _special_keys_down = new HashSet<>();
        _previous_keys_down = new HashSet<>();
        _previous_special_keys_down = new HashSet<>();
    }

    public void pollInput() {
        try {
            _previous_keys_down.clear();
            _previous_keys_down.addAll(_keys_down);
            _previous_special_keys_down.clear();
            _previous_special_keys_down.addAll(_special_keys_down);

            _keys_down.clear();
            _special_keys_down.clear();

            KeyStroke key_stroke;
            while ((key_stroke = _screen.pollInput()) != null) {
                if (key_stroke.getCharacter() != null) {
                    _keys_down.add(key_stroke.getCharacter());
                }

                if (key_stroke.getKeyType() != null) {
                    _special_keys_down.add(key_stroke.getKeyType());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isKeyDown(char key) {
        return _keys_down.contains(key)
                || _keys_down.contains(Character.toLowerCase(key))
                || _keys_down.contains(Character.toUpperCase(key));
    }

    public boolean isKeyDown(KeyType key_type) {
        return _special_keys_down.contains(key_type);
    }

    public boolean isKeyJustPressed(char key) {
        char lower = Character.toLowerCase(key);
        char upper = Character.toUpperCase(key);

        boolean is_down = _keys_down.contains(key)
                || _keys_down.contains(lower)
                || _keys_down.contains(upper);

        boolean was_down = _previous_keys_down.contains(key)
                || _previous_keys_down.contains(lower)
                || _previous_keys_down.contains(upper);

        return is_down && !was_down;
    }

    public boolean isKeyJustPressed(KeyType key_type) {
        return _special_keys_down.contains(key_type)
                && !_previous_special_keys_down.contains(key_type);
    }

    public boolean isKeyJustReleased(char key) {
        char lower = Character.toLowerCase(key);
        char upper = Character.toUpperCase(key);

        boolean is_down = _keys_down.contains(key)
                || _keys_down.contains(lower)
                || _keys_down.contains(upper);

        boolean was_down = _previous_keys_down.contains(key)
                || _previous_keys_down.contains(lower)
                || _previous_keys_down.contains(upper);

        return !is_down && was_down;
    }

    public boolean isKeyJustReleased(KeyType key_type) {
        return !_special_keys_down.contains(key_type)
                && _previous_special_keys_down.contains(key_type);
    }

    public boolean isUpPressed() {
        return isKeyDown(KeyType.ArrowUp) || isKeyDown('w') || isKeyDown('W');
    }

    public boolean isDownPressed() {
        return isKeyDown(KeyType.ArrowDown) || isKeyDown('s') || isKeyDown('S');
    }

    public boolean isLeftPressed() {
        return isKeyDown(KeyType.ArrowLeft) || isKeyDown('a') || isKeyDown('A');
    }

    public boolean isRightPressed() {
        return isKeyDown(KeyType.ArrowRight) || isKeyDown('d') || isKeyDown('D');
    }

    public boolean isUpJustPressed() {
        return isKeyJustPressed(KeyType.ArrowUp) || isKeyJustPressed('w') || isKeyJustPressed('W');
    }

    public boolean isDownJustPressed() {
        return isKeyJustPressed(KeyType.ArrowDown) || isKeyJustPressed('s') || isKeyJustPressed('S');
    }

    public boolean isLeftJustPressed() {
        return isKeyJustPressed(KeyType.ArrowLeft) || isKeyJustPressed('a') || isKeyJustPressed('A');
    }

    public boolean isRightJustPressed() {
        return isKeyJustPressed(KeyType.ArrowRight) || isKeyJustPressed('d') || isKeyJustPressed('D');
    }

    public boolean isUpJustReleased() {
        return isKeyJustReleased(KeyType.ArrowUp) || isKeyJustReleased('w') || isKeyJustReleased('W');
    }

    public boolean isDownJustReleased() {
        return isKeyJustReleased(KeyType.ArrowDown) || isKeyJustReleased('s') || isKeyJustReleased('S');
    }

    public boolean isLeftJustReleased() {
        return isKeyJustReleased(KeyType.ArrowLeft) || isKeyJustReleased('a') || isKeyJustReleased('A');
    }

    public boolean isRightJustReleased() {
        return isKeyJustReleased(KeyType.ArrowRight) || isKeyJustReleased('d') || isKeyJustReleased('D');
    }
}

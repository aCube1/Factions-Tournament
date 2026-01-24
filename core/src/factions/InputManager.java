package factions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

// Done with help of Copilot
public class InputManager implements InputProcessor {
    private static InputManager _instance;

    private ObjectMap<String, Array<Integer>> _action_keys;

    private ObjectMap<String, Boolean> _action_pressed;
    private ObjectMap<String, Boolean> _action_just_pressed;
    private ObjectMap<String, Boolean> _action_just_released;

    // Action queue for next update
    private Array<String> _on_frame_just_pressed;
    private Array<String> _on_frame_just_released;

    public InputManager() {
        _action_keys = new ObjectMap<>();
        _action_pressed = new ObjectMap<>();
        _action_just_pressed = new ObjectMap<>();
        _action_just_released = new ObjectMap<>();
        _on_frame_just_pressed = new Array<>();
        _on_frame_just_released = new Array<>();
    }

    public static InputManager getInstance() {
        if (_instance == null) {
            _instance = new InputManager();
        }

        return _instance;
    }

    public void addAction(String action, int... keys) {
        if (action.isBlank())
            return;

        Array<Integer> key_array = new Array<>();
        for (int key : keys) {
            key_array.add(key);
        }

        _action_keys.put(action, key_array);
        _action_pressed.put(action, false);
        _action_just_pressed.put(action, false);
        _action_just_released.put(action, false);
    }

    public void update() {
        for (String action : _action_just_pressed.keys()) {
            _action_just_pressed.put(action, false);
        }

        for (String action : _action_just_released.keys()) {
            _action_just_released.put(action, false);
        }

        for (String action : _on_frame_just_pressed) {
            _action_just_pressed.put(action, true);
        }

        for (String action : _on_frame_just_released) {
            _action_just_released.put(action, true);
        }

        _on_frame_just_pressed.clear();
        _on_frame_just_released.clear();
    }

    public boolean isActionDown(String action) {
        return _action_pressed.get(action, false);
    }

    public boolean isActionJustPressed(String action) {
        return _action_just_pressed.get(action, false);
    }

    public boolean isActionJustReleased(String action) {
        return _action_just_released.get(action, false);
    }

    private Array<String> getActionsForKey(int keycode) {
        Array<String> actions = new Array<>();
        for (ObjectMap.Entry<String, Array<Integer>> entry : _action_keys) {
            if (entry.value.contains(keycode, false)) {
                actions.add(entry.key);
            }
        }
        return actions;
    }

    private boolean isActionStillPressed(String action, int exclude_key) {
        Array<Integer> keys = _action_keys.get(action);
        if (keys != null) {
            for (int key : keys) {
                if (key == exclude_key || !Gdx.input.isKeyPressed(key)) {
                    continue;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        Array<String> actions = getActionsForKey(keycode);
        for (String action : actions) {
            boolean was_pressed = _action_pressed.get(action, false);
            _action_pressed.put(action, true);

            if (!was_pressed) {
                _on_frame_just_pressed.add(action);
            }
        }

        return actions.size > 0;
    }

    @Override
    public boolean keyUp(int keycode) {
        Array<String> actions = getActionsForKey(keycode);
        for (String action : actions) {
            // If registered key in action is pressed, but action is
            // still being executed, do not register it as released
            if (isActionStillPressed(action, keycode)) {
                continue;
            }

            if (_action_pressed.get(action, false)) {
                _action_pressed.put(action, false);
                _on_frame_just_released.add(action);
            }
        }

        return actions.size > 0;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

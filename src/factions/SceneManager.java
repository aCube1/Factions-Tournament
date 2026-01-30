package factions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// I lov ma StateMachine: https://github.com/aCube1/StateMachine
// Just a lil' bit simplified
public class SceneManager {
    private Map<String, IScene> _scenes;
    private IScene _current;
    private String _current_state;
    private String _next_state;
    private boolean _is_next_state_transition;

    public SceneManager() {
        _scenes = new HashMap<>();
        _current = null;
        _current_state = null;
        _next_state = null;
        _is_next_state_transition = false;
    }

    public void addScene(String name, IScene scene) {
        if (name.isBlank())
            return;

        scene.setSceneManager(this);
        _scenes.put(name, scene);
    }

    public boolean switchScene(String name) {
        return switchScene(name, false);
    }

    public boolean switchScene(String name, boolean is_transition) {
        if (!_scenes.containsKey(name) && !is_transition) {
            System.err.println("Cannot find scene: " + name);
            return false;
        }

        _next_state = name;
        _is_next_state_transition = is_transition;
        return true;
    }

    public void update() {
        if (_next_state != null) {
            if (_current != null)
                _current.onExit();

            _current_state = _next_state;
            if (!_is_next_state_transition) {
                _current = _scenes.get(_next_state);
                _current.onEnter();
            }
            _next_state = null;
        }

        if (_current != null)
            _current.update();
    }

    public void render() throws IOException {
        if (_current == null)
            return;

        _current.render();
    }

    public IScene getCurrentScene() {
        return _current;
    }

    public String getCurrentSceneName() {
        return _current_state;
    }

    public String getNextSceneName() {
        return _next_state;
    }
}

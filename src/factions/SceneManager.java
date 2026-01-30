package factions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.googlecode.lanterna.screen.Screen;

// I lov ma StateMachine: https://github.com/aCube1/StateMachine
// Just a lil' bit simplified
public class SceneManager {
    private Map<String, IScene> _scenes;
    private IScene _current;
    private String _current_name;

    public SceneManager() {
        _scenes = new HashMap<>();
        _current = null;
        _current_name = null;
    }

    public void addScene(String name, IScene scene) {
        if (name.isBlank())
            return;

        _scenes.put(name, scene);
        if (_current == null)
            switchScene(name);
    }

    public boolean switchScene(String name) {
        if (!_scenes.containsKey(name)) {
            System.err.println("Cannot find scene: " + name);
            return false;
        }

        if (_current != null)
            _current.onExit();

        _current_name = name;
        _current = _scenes.get(name);
        _current.onEnter();
        return true;
    }

    public void update() {
        if (_current_name == null)
            return;

        _current.update();
        String next = _current.getNextScene();
        if (next != null && !next.isBlank() && !next.equals(_current_name)) {
            switchScene(next);
        }
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
        return _current_name;
    }
}

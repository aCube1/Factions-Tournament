package factions;

import java.io.IOException;

// I lov ma StateMachine: https://github.com/aCube1/StateMachine
public interface IScene {
    void update();

    void render() throws IOException;

    void onEnter();

    void onExit();

    void setSceneManager(SceneManager manager);
}

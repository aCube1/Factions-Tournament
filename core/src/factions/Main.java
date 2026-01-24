package factions;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private InputManager _input;
    private SpriteBatch _batch;
    private Arena _arena;

    private Color _color = Color.DARK_GRAY;

    @Override
    public void create() {
        _input = InputManager.getInstance();
        Gdx.input.setInputProcessor(_input);

        _batch = new SpriteBatch();
        _arena = new Arena();

        // TEST: Just to test input manager
        _input.addAction("Change Color", Keys.SPACE, Keys.A);
    }

    @Override
    public void render() {
        _input.update();

        ScreenUtils.clear(_color);
        _batch.begin();
        _batch.end();

        // TEST: Just to test input manager
        if (_input.isActionJustPressed("Change Color")) {
            if (_color == Color.DARK_GRAY)
                _color = Color.LIGHT_GRAY;
            else
                _color = Color.DARK_GRAY;
        }
    }

    @Override
    public void dispose() {
    }
}

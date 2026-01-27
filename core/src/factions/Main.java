package factions;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import factions.controllers.*;

public class Main extends ApplicationAdapter {
    private InputManager _input;
    private SpriteBatch _batch;
    private Arena _arena;

    private PlayerController _player;
    private AIController _main_ai;

    @Override
    public void create() {
        _input = InputManager.getInstance();
        Gdx.input.setInputProcessor(_input);

        _batch = new SpriteBatch();
        _arena = new Arena();
    }

    @Override
    public void render() {
        _input.update();

        // TODO: Check both player's and AI's commands
        // and compute current turn

        _player.update(_input);
        _main_ai.update(_input);

        _arena.computeTurn(_player);
        _arena.computeTurn(_main_ai);

        ScreenUtils.clear(Color.DARK_GRAY);
        _batch.begin();
        // TODO: Draw arena, buttons, characters and everything else...
        _batch.end();
    }

    @Override
    public void dispose() {
    }
}

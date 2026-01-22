package factions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
    }

    @Override
    public void render() {
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
    }
}

package factions;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class MainMenu implements IScene {

    private SceneManager manager;
    private Screen screen;
    private Layout layout;
    private Rules rules;

    public MainMenu(Screen screen) {
        this.screen = screen;
        this.layout = new Layout();
        this.rules = new Rules();
    }

    @Override
    public void update() {
        try {
            KeyStroke key = screen.pollInput();

            if (key == null)
                return;

            if (key.getKeyType() == KeyType.Character) {
                char c = key.getCharacter();

                if (c == '1') {
                    manager.switchScene("game");
                } else if (c == '2') {
                    manager.switchScene("exit", true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() throws IOException {
        screen.clear();
        var gfx = screen.newTextGraphics();

        gfx.putString(0, 0, layout.layout());
        gfx.putString(0, 6, rules.getRules());
        gfx.putString(0, 14, rules.getDescriptionCharacters());

        screen.refresh();
    }

    @Override
    public void onEnter() {
        System.out.println("Conectando ao Mundo Mágico...");
    }

    @Override
    public void onExit() {
        System.out.println("Saindo do jogo...");
    }

    @Override
    public void setSceneManager(SceneManager manager) {
        this.manager = manager;
    }
}

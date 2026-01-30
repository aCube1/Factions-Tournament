package factions.scenes;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import factions.IScene;
import factions.Rules;
import factions.SceneManager;

public class RulesScene implements IScene {
    private Screen _screen;
    private SceneManager _scene_manager;
    private Rules _rules;

    public RulesScene(Screen screen) {
        _screen = screen;
        _rules = new Rules();
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void setSceneManager(SceneManager manager) {
        _scene_manager = manager;
    }

    @Override
    public void update() {
        try {
            KeyStroke key = _screen.pollInput();

            if (key != null) {
                if (key.getKeyType() == KeyType.Escape || key.getKeyType() == KeyType.Enter) {
                    if (_scene_manager != null) {
                        _scene_manager.switchScene("main_menu");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() throws IOException {
        _screen.clear();
        TextGraphics graphics = _screen.newTextGraphics();
        TerminalSize size = _screen.getTerminalSize();

        // Draw title
        drawCenteredText(graphics, 2, "=== REGRAS DO JOGO ===", TextColor.ANSI.YELLOW);

        // Draw rules
        String rulesText = _rules.getRules();
        String[] rulesLines = rulesText.split("\n");
        int startY = 4;

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        for (String line : rulesLines) {
            if (startY < size.getRows() - 8) {
                graphics.putString(5, startY, line);
                startY++;
            }
        }

        // Draw character descriptions
        startY += 2;
        drawCenteredText(graphics, startY, "=== PERSONAGENS ===", TextColor.ANSI.CYAN);
        startY += 2;

        String descText = _rules.getDescriptionCharacters();
        String[] descLines = descText.split("\n");

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        for (String line : descLines) {
            if (startY < size.getRows() - 3) {
                graphics.putString(5, startY, line);
                startY++;
            }
        }

        // Draw controls
        drawCenteredText(graphics, size.getRows() - 2, "Pressione ESC ou ENTER para voltar ao menu", TextColor.ANSI.GREEN);

        _screen.refresh();
    }

    private void drawCenteredText(TextGraphics graphics, int y, String text, TextColor color) {
        TerminalSize size = _screen.getTerminalSize();
        int x = Math.max(0, (size.getColumns() - text.length()) / 2);
        graphics.setForegroundColor(color);
        graphics.putString(x, y, text);
    }
}

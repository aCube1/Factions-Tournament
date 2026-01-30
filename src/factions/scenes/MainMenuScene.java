package factions.scenes;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import factions.IScene;
import factions.Layout;
import factions.SceneManager;

public class MainMenuScene implements IScene {
    private Screen _screen;
    private SceneManager _scene_manager;
    private Layout _layout;

    private int _selected_option;
    private static final String[] MENU_OPTIONS = {
        "Iniciar Batalha",
        "Selecao de Personagens",
        "Regras",
        "Sair"
    };

    public MainMenuScene(Screen screen) {
        _screen = screen;
        _layout = new Layout();
        _selected_option = 0;
    }

    @Override
    public void onEnter() {
        _selected_option = 0;
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
                if (key.getKeyType() == KeyType.ArrowUp) {
                    _selected_option = (_selected_option - 1 + MENU_OPTIONS.length) % MENU_OPTIONS.length;
                } else if (key.getKeyType() == KeyType.ArrowDown) {
                    _selected_option = (_selected_option + 1) % MENU_OPTIONS.length;
                } else if (key.getKeyType() == KeyType.Enter) {
                    handleSelection();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSelection() {
        if (_scene_manager == null) {
            return;
        }

        switch (_selected_option) {
            case 0: // Iniciar Batalha
                _scene_manager.switchScene("battle");
                break;
            case 1: // Seleção de Personagens
                _scene_manager.switchScene("character_select");
                break;
            case 2: // Regras
                _scene_manager.switchScene("rules");
                break;
            case 3: // Sair
                _scene_manager.switchScene("exit");
                break;
        }
    }

    @Override
    public void render() throws IOException {
        _screen.clear();
        TextGraphics graphics = _screen.newTextGraphics();
        TerminalSize size = _screen.getTerminalSize();

        // Draw title
        String title = "FACTIONS TOURNAMENT";
        drawCenteredText(graphics, 3, title, TextColor.ANSI.YELLOW);

        // Draw welcome message
        String[] welcomeLines = _layout.layout().split("\n");
        int startY = 6;
        for (String line : welcomeLines) {
            if (!line.trim().isEmpty()) {
                drawCenteredText(graphics, startY, line, TextColor.ANSI.WHITE);
                startY++;
            }
        }

        // Draw menu options
        int menuStartY = startY + 2;
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            if (i == _selected_option) {
                graphics.setForegroundColor(TextColor.ANSI.YELLOW);
                drawCenteredText(graphics, menuStartY + i, "> " + MENU_OPTIONS[i] + " <", TextColor.ANSI.YELLOW);
            } else {
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                drawCenteredText(graphics, menuStartY + i, "  " + MENU_OPTIONS[i] + "  ", TextColor.ANSI.WHITE);
            }
        }

        // Draw controls
        drawCenteredText(graphics, size.getRows() - 2, "Use as setas para navegar, ENTER para selecionar", TextColor.ANSI.GREEN);

        _screen.refresh();
    }

    private void drawCenteredText(TextGraphics graphics, int y, String text, TextColor color) {
        TerminalSize size = _screen.getTerminalSize();
        int x = Math.max(0, (size.getColumns() - text.length()) / 2);
        graphics.setForegroundColor(color);
        graphics.putString(x, y, text);
    }
}

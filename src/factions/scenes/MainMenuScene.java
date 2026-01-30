package factions.scenes;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.SameTextGUIThread;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;

import factions.IScene;

public class MainMenuScene implements IScene {
    private Screen _screen;
    private MultiWindowTextGUI _gui;
    private BasicWindow _window;
    private String _next_scene;

    public MainMenuScene(Screen screen) {
        _screen = screen;
        _next_scene = null;
    }

    private Panel createMenu() {
        Panel main = new Panel();
        main.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        String title = new String();
        title += "╔═════════════════════════╗\n";
        title += "║                         ║\n";
        title += "║   Factions Tournament   ║\n";
        title += "║                         ║\n";
        title += "╚═════════════════════════╝\n";
        Label title_label = new Label(title);
        title_label.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        main.addComponent(title_label);

        main.addComponent(new EmptySpace(new TerminalSize(0, 2)));

        // Buttons
        Button start_button = new Button("▶ Start Game", () -> {
            _next_scene = "character_select";
        });
        start_button.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Button exit_button = new Button(" Exit", () -> {
            _next_scene = "exit";
        });
        exit_button.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        main.addComponent(start_button);
        main.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        main.addComponent(exit_button);
        main.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        return main;
    }

    private Panel createFooter() {
        Panel footer = new Panel();
        footer.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Separator separator = new Separator(Direction.HORIZONTAL);
        footer.addComponent(separator);

        Panel footer_content = new Panel();
        footer_content.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Label controls = new Label("Controls: ↑↓ Navigate | ENTER Select | ESC Back");
        controls.setForegroundColor(new TextColor.RGB(150, 150, 150));

        footer_content.addComponent(controls);
        footer_content.addComponent(new EmptySpace(new TerminalSize(5, 1)));

        Label version = new Label("v1.0");
        version.setForegroundColor(new TextColor.RGB(100, 100, 100));

        footer_content.addComponent(version);
        footer.addComponent(footer_content);

        return footer;
    }

    @Override
    public void update() {
    }

    @Override
    public void render() throws IOException {
        _screen.clear();
        _gui.updateScreen();
        _screen.refresh();
    }

    @Override
    public void onEnter() {
        _next_scene = null;

        _gui = new MultiWindowTextGUI(new SameTextGUIThread.Factory(), _screen);
        _gui.setTheme(LanternaThemes.getRegisteredTheme("blaster"));

        _window = new BasicWindow();
        _window.setHints(Arrays.asList(
                Window.Hint.FULL_SCREEN, Window.Hint.MODAL));

        Panel container = new Panel();
        container.setLayoutManager(new BorderLayout());

        container.addComponent(createMenu(), BorderLayout.Location.CENTER);
        container.addComponent(createFooter(), BorderLayout.Location.BOTTOM);

        _window.setComponent(container);
        _gui.addWindow(_window);
    }

    @Override
    public void onExit() {
        if (_window != null)
            _window.close();
    }

    @Override
    public String getNextScene() {
        return _next_scene;
    }
}

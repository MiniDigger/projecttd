package me.minidigger.projecttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

/**
 * Created by Martin on 29/04/2017.
 */
public class MainMenuScreen implements Screen {

    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VisUI.load();

        VisTable table = new VisTable(true);

        VisTextButton newGameButton = new VisTextButton("New game");
        newGameButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 100, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        table.add(newGameButton);
        stage.addActor(newGameButton);

        VisTextButton levelSelectButton = new VisTextButton("Level Select");
        levelSelectButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 50, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        levelSelectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("level select");// TODO level select
            }
        });
        table.add(levelSelectButton);
        stage.addActor(levelSelectButton);

        VisTextButton levelEditorButton = new VisTextButton("Level Editor");
        levelEditorButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 0, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        levelEditorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("level editor");//TODO level editor
            }
        });
        table.add(levelEditorButton);
        stage.addActor(levelEditorButton);

        VisTextButton settingsButton = new VisTextButton("Settings");
        settingsButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) - 50, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("settings"); // TODO settings
            }
        });
        table.add(settingsButton);
        stage.addActor(settingsButton);

        VisTextButton creditsButton = new VisTextButton("Credits");
        creditsButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) - 100, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("credits"); //TODO credits
            }
        });
        table.add(creditsButton);
        stage.addActor(creditsButton);

        VisTextButton quitButton = new VisTextButton("Quit");
        quitButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) - 150, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(quitButton);
        stage.addActor(quitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        VisUI.dispose();
    }

    @Override
    public void dispose() {

    }
}

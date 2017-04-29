package me.minidigger.projecttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

/**
 * Created by Martin on 29/04/2017.
 */
public class CreditsScreen implements Screen {
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VisUI.load();

        VisTable table = new VisTable(true);

        VisLabel heading = new VisLabel("Credits");
        heading.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 100, 200, 50);
        heading.setColor(Color.BLACK);
        table.add(heading);
        stage.addActor(heading);

        VisLabel temp = new VisLabel("To Be Implemented");
        temp.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 0, 200, 50);
        temp.setColor(Color.BLACK);
        table.add(temp);
        stage.addActor(temp);

        VisTextButton newGameButton = new VisTextButton("Back");
        newGameButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) - 100, 200, 50);
        //   newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        table.add(newGameButton);
        stage.addActor(newGameButton);
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
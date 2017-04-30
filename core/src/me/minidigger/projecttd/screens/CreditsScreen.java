package me.minidigger.projecttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.CenteredTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
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

        CenteredTableBuilder tableBuilder = new CenteredTableBuilder(new Padding(2, 3));

        VisLabel heading = new VisLabel("Credits");
        heading.setColor(Color.BLACK);
        tableBuilder.append(heading).row();
        stage.addActor(heading);

        VisLabel temp = new VisLabel("To Be Implemented");
        temp.setColor(Color.BLACK);
        tableBuilder.append(temp).row();
        stage.addActor(temp);

        VisTextButton backButton = new VisTextButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        tableBuilder.append(backButton).row();
        stage.addActor(backButton);

        Table table = tableBuilder.build();
        table.setFillParent(true);
        stage.addActor(table);
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
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
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.widget.*;
import me.minidigger.projecttd.level.Level;
import me.minidigger.projecttd.level.LevelManager;

/**
 * Created by Martin on 29/04/2017.
 */
public class LeveSelectScreen implements Screen {

    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VisUI.load();

        VisTable table = new VisTable(true);

        VisLabel heading = new VisLabel("Level Select");
        heading.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 100, 200, 50);
        heading.setColor(Color.BLACK);
        table.add(heading);
        stage.addActor(heading);

        GridGroup grid = new GridGroup(50, 5);
        grid.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 50, 200, 200);
        for (Level level : LevelManager.getInstance().getLevels()) {
            VisImageButton levelButton = new VisImageButton(level.getThumbnail(), level.getName());
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("clicked " + level.getName());
                }
            });
            //TODO unfuck this
            levelButton.setBounds((Gdx.graphics.getWidth() / 2f) - 100, (Gdx.graphics.getHeight() / 2f) + 50, 50, 50);
            grid.addActor(levelButton);
            stage.addActor(levelButton);
        }
        table.addActor(grid);
        stage.addActor(grid);

        VisScrollPane scrollPane = new VisScrollPane(grid);
        scrollPane.setScrollingDisabled(true, false); //disable X scrolling
        table.add(scrollPane);
        stage.addActor(scrollPane);

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
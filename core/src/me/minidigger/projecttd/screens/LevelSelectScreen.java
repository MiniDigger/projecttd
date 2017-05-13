package me.minidigger.projecttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.CenteredTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.widget.*;
import me.minidigger.projecttd.level.Level;
import me.minidigger.projecttd.level.LevelManager;

/**
 * Created by Martin on 29/04/2017.
 */
public class LevelSelectScreen implements Screen {

    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VisUI.load();

        CenteredTableBuilder tableBuilder = new CenteredTableBuilder(new Padding(2, 3));

        VisLabel heading = new VisLabel("Level Select");
        heading.setColor(Color.BLACK);
        tableBuilder.append(heading).row();
        stage.addActor(heading);

        for (Level level : LevelManager.getInstance().getLevels()) {
            Texture texture = new Texture(Gdx.files.internal(level.getThumbnail()));
            Drawable thumb = new TextureRegionDrawable(new TextureRegion(texture));
            VisImageButton levelButton = new VisImageButton(thumb, level.getName());
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameScreen gameScreen = new GameScreen();
                    gameScreen.level = level;
                    ((Game) Gdx.app.getApplicationListener()).setScreen(gameScreen);
                }
            });
            tableBuilder.append(levelButton);
            stage.addActor(levelButton);
        }
        tableBuilder.row();

//        VisScrollPane scrollPane = new VisScrollPane(grid);
//        scrollPane.setScrollingDisabled(true, false); //disable X scrolling
//        table.add(scrollPane);
//        stage.addActor(scrollPane);

        VisTextButton newGameButton = new VisTextButton("Back");
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        tableBuilder.append(newGameButton).row();
        stage.addActor(newGameButton);

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
package me.minidigger.projecttd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Martin on 02.04.2017.
 */
public class GameInputProcessor extends InputAdapter {

    private ProjectTD game;

    public GameInputProcessor(ProjectTD projectTD) {
        game = projectTD;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        game.gameScreen.debugTouch(screenX, screenY, pointer, button);

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                game.gameScreen.toggleDebugRendering();
            }
        }

        return true;
    }
}

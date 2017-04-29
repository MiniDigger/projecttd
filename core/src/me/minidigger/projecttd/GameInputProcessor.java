package me.minidigger.projecttd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import me.minidigger.projecttd.screens.GameScreen;

/**
 * Created by Martin on 02.04.2017.
 */
public class GameInputProcessor extends InputAdapter {

    private GameScreen gameScreen;

    public GameInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameScreen.debugTouch(screenX, screenY, pointer, button);

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                gameScreen.toggleDebugRendering();
            }
        }

        return true;
    }
}

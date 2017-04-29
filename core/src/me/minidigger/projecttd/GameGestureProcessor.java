package me.minidigger.projecttd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import me.minidigger.projecttd.screens.GameScreen;

/**
 * Created by Martin on 02.04.2017.
 */
public class GameGestureProcessor extends GestureDetector.GestureAdapter {

    private GameScreen gameScreen;

    public GameGestureProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        gameScreen.panCamera(deltaX);
        Gdx.app.debug("PTD", "Pan " + deltaX);
        return true;
    }
}

package me.minidigger.projecttd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Martin on 02.04.2017.
 */
public class GameGestureProcessor extends GestureDetector.GestureAdapter {

    private ProjectTD game;

    public GameGestureProcessor(ProjectTD projectTD) {
        game = projectTD;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        game.gameScreen.panCamera(deltaX);
        Gdx.app.debug("PTD", "Pan " + deltaX);
        return true;
    }
}

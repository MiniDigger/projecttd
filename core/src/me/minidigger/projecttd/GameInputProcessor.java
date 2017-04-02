package me.minidigger.projecttd;

import com.badlogic.gdx.InputAdapter;

/**
 * Created by Martin on 02.04.2017.
 */
public class GameInputProcessor extends InputAdapter {

    private ProjectTD game;

    public GameInputProcessor(ProjectTD projectTD) {
        game = projectTD;
    }
}

package me.minidigger.projecttd;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

import me.minidigger.projecttd.screens.GameScreen;

public class ProjectTD extends Game {

    public GameScreen gameScreen;

    @Override
    public void create() {
        // input
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new GameGestureProcessor(this)));
        multiplexer.addProcessor(new GameInputProcessor(this));
        Gdx.input.setInputProcessor(multiplexer);

        setScreen(gameScreen = new GameScreen());
    }
}

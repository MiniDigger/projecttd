package me.minidigger.projecttd;

import com.badlogic.gdx.Game;

import me.minidigger.projecttd.screens.GameScreen;

public class ProjectTD extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}

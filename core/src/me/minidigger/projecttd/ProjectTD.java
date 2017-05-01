package me.minidigger.projecttd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import me.minidigger.projecttd.screens.MainMenuScreen;

public class ProjectTD extends Game {

    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 480;

    @Override
    public void create() {
        //Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new MainMenuScreen());
    }
}

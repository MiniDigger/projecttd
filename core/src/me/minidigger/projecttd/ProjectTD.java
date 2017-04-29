package me.minidigger.projecttd;

import com.badlogic.gdx.Game;
import me.minidigger.projecttd.screens.MainMenuScreen;

public class ProjectTD extends Game {

    @Override
    public void create() {
        //Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new MainMenuScreen());
    }
}

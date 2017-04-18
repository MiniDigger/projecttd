package me.minidigger.projecttd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.minidigger.projecttd.ProjectTD;

public class DesktopLauncher {

    public static void main(String[] arg) {
        //System.setProperty("org.lwjgl.util.Debug", "true");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new ProjectTD(), config);
    }
}

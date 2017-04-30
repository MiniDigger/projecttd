package me.minidigger.projecttd.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import me.minidigger.projecttd.ProjectTD;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(ProjectTD.V_WIDTH, ProjectTD.V_HEIGHT);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new ProjectTD();
    }
}
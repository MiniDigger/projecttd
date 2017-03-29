package me.minidigger.projecttd.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import me.minidigger.projecttd.ProjectTD;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        Test test = () -> System.out.println("test");
        test.test();
        return new ProjectTD();
    }

    @FunctionalInterface
    interface Test {
        void test();
    }
}
package me.minidigger.projecttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Martin on 01.04.2017.
 */
public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private int mapHeight;
    private int mapWidth;

    @Override
    public void show() {
        map = new TmxMapLoader().load("maps/map01.tmx");

        mapHeight = map.getProperties().get("height", 40, int.class);
        mapWidth = map.getProperties().get("width", 15, int.class);

        float unitScale = 1 / map.getProperties().get("tilewidth", 128, int.class).floatValue();

        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapWidth, mapHeight);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x++;
            if (camera.position.x < 0) {
                camera.position.x = 0;
            }
        }

        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

        float zoom = mapHeight / ((float) height);
        camera.zoom = zoom;

        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public void panCamera(float deltaX) {
        camera.position.x -= deltaX / 50;

        float effectiveWidth = camera.viewportWidth * camera.zoom;
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveWidth / 2f, mapWidth - effectiveWidth / 2f);

        camera.update();
    }
}

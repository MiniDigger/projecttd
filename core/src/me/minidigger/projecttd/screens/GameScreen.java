package me.minidigger.projecttd.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

import me.minidigger.projecttd.components.PositionComponent;
import me.minidigger.projecttd.components.RotationComponent;
import me.minidigger.projecttd.components.SpriteComponent;
import me.minidigger.projecttd.components.VelocityComponent;
import me.minidigger.projecttd.systems.MovementSystem;
import me.minidigger.projecttd.systems.RenderSystem;

/**
 * Created by Martin on 01.04.2017.
 */
public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private int mapHeight;
    private int mapWidth;

    private PooledEngine engine;

    private Sprite minionSprite;

    @Override
    public void show() {
        // map
        map = new TmxMapLoader().load("maps/map01.tmx");

        mapHeight = map.getProperties().get("height", 40, int.class);
        mapWidth = map.getProperties().get("width", 15, int.class);

        // renderer
        float unitScale = 1 / map.getProperties().get("tilewidth", 128, int.class).floatValue();
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapWidth, mapHeight);
        camera.update();

        // ecs
        engine = new PooledEngine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(camera));

        loadSprites();

        Entity test = getMinion();
        test.getComponent(PositionComponent.class).set(-50, -60);
        test.getComponent(VelocityComponent.class).x = 1;
    }

    private void loadSprites() {
        Texture texture = new Texture(Gdx.files.internal("tileset.png"));
        minionSprite = new Sprite(texture, 15 * 128 + 1, 10 * 128 + 1, 128, 128);
        float scale = 2;
        minionSprite.setScale((mapHeight / (float) Gdx.graphics.getHeight()) / scale);
        System.out.println("scale = " + minionSprite.getScaleX());
    }

    public Entity getMinion() {
        Entity entity = engine.createEntity();

        entity.add(new PositionComponent());
        entity.add(new VelocityComponent());
        entity.add(new SpriteComponent(minionSprite));
        entity.add(new RotationComponent());

        engine.addEntity(entity);
        return entity;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        engine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

        camera.zoom = mapHeight / ((float) height);
        System.out.println(camera.zoom);

        panCamera(0);// clamp

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

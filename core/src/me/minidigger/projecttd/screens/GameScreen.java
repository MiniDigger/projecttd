package me.minidigger.projecttd.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.entities.Minion;
import me.minidigger.projecttd.systems.MoveToSystem;
import me.minidigger.projecttd.systems.MovementSystem;
import me.minidigger.projecttd.systems.RenderSystem;
import me.minidigger.projecttd.utils.CoordinateUtil;

/**
 * Created by Martin on 01.04.2017.
 */
public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private int mapHeight;
    private int mapWidth;

    private PooledEngine engine;

    private Sprite minionSprite;

    private Vector2 touchPoint = new Vector2();
    private Entity minion;

    @Override
    public void show() {
        // map
        map = new TmxMapLoader().load("maps/map01.tmx");

        mapHeight = map.getProperties().get("height", 40, int.class);
        mapWidth = map.getProperties().get("width", 15, int.class);

        // renderer
        float unitScale = 1 / map.getProperties().get("tilewidth", 128, int.class).floatValue();
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        shapeRenderer = new ShapeRenderer();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapWidth, mapHeight);
        camera.update();

        // ecs
        engine = new PooledEngine();
        engine.addSystem(new MoveToSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(camera));

        loadSprites();
        setupEntities();

        minion = Minion.newMinion(new Vector2(10, 10), new Vector2(-40, -70));
        //    minion = Minion.newMinion(new Vector2(-50, -60), new Vector2(-40, -70));
//        Entity test = Minion.newMinion(new Vector2(-50, -60), null);
    }

    private void setupEntities() {
        Minion.ENGINE = engine;
        Minion.SPRITE = minionSprite;
    }

    private void loadSprites() {
        Texture texture = new Texture(Gdx.files.internal("tileset.png"));
        minionSprite = new Sprite(texture, 15 * 128 + 1, 10 * 128 + 1, 128, 128);
        float scale = 2;
        minionSprite.setScale((mapHeight / (float) Gdx.graphics.getHeight()) / scale);
        minionSprite.setCenter(128 / 2, 128 / 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        engine.update(delta);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(touchPoint.x, touchPoint.y, 0.5f, 16);
        shapeRenderer.end();
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

    public void debugTouch(int screenX, int screenY, int pointer, int button) {
        System.out.println("================================");
        System.out.println("touch " + "(" + screenX + "," + screenY + ")");
        CoordinateUtil.touchToWorld(touchPoint.set(screenX, screenY), camera);
        System.out.println("render " + touchPoint);
        System.out.println("position " + Minion.getTransform(minion).position);
        Minion.getTarget(minion).target.set(touchPoint);
    }
}

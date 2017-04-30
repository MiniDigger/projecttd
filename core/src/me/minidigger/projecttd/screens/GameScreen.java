package me.minidigger.projecttd.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.GameGestureProcessor;
import me.minidigger.projecttd.GameInputProcessor;
import me.minidigger.projecttd.entities.Minion;
import me.minidigger.projecttd.entities.Tower;
import me.minidigger.projecttd.systems.*;
import me.minidigger.projecttd.utils.CoordinateUtil;

/**
 * Created by Martin on 01.04.2017.
 */
public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font = new BitmapFont();
    private SpriteBatch spriteBatch = new SpriteBatch();

    private int mapHeight;
    private int mapWidth;

    private int tilewidth;

    private PooledEngine engine;

    private Sprite minionSprite;
    private Sprite towerSprite;

    private Vector2 touchPoint = new Vector2();
    private Vector2 spawnPoint = new Vector2();
    private boolean debugRendering = false;

    private PathFindingSystem pathFindingSystem;
    private TurretSystem turretSystem;

    @Override
    public void show() {
        //input
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new GameGestureProcessor(this)));
        multiplexer.addProcessor(new GameInputProcessor(this));
        Gdx.input.setInputProcessor(multiplexer);

        // map
        map = new TmxMapLoader().load("maps/map01.tmx");

        mapHeight = map.getProperties().get("height", 40, int.class);
        mapWidth = map.getProperties().get("width", 15, int.class);

        tilewidth = map.getProperties().get("tilewidth", 128, int.class);

        // renderer
        float unitScale = 1 / (float) tilewidth;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        shapeRenderer = new ShapeRenderer();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapWidth, mapHeight);
        camera.update();

        // ecs
        engine = new PooledEngine();
        engine.addSystem(new SpawnSystem(engine, mapHeight, 0.5f));
        engine.addSystem(turretSystem = new TurretSystem());
        engine.addSystem(pathFindingSystem = new PathFindingSystem(mapHeight, mapWidth, map));
        engine.addSystem(new MoveToSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(camera, tilewidth));

        loadSprites();
        setupEntities();

        pathFindingSystem.init(new Vector2(39.5f, mapHeight - 10 + 0.5f));
    }

    private void setupEntities() {
        Minion.ENGINE = engine;
        Minion.SPRITE = minionSprite;

        Tower.ENGINE = engine;
        Tower.SPRITE = towerSprite;
    }

    private void loadSprites() {
        Texture texture = new Texture(Gdx.files.internal("tileset.png"));

        minionSprite = new Sprite(texture, 15 * tilewidth + 1, 10 * tilewidth + 1, tilewidth, tilewidth);
        float scale = 2;
        minionSprite.setScale((mapHeight / (float) Gdx.graphics.getHeight()) / scale);

        towerSprite = new Sprite(texture, 19 * tilewidth + 1, 7 * tilewidth + 1, tilewidth, tilewidth);
        scale = 3;
        towerSprite.setScale((mapHeight / (float) Gdx.graphics.getHeight()) / scale);
    }

    @Override
    public void render(float delta) {
        try {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();

            engine.update(delta);

            shapeRenderer.setProjectionMatrix(camera.combined);

            if (debugRendering) {
                // touch pos
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(touchPoint.x, touchPoint.y, 0.1f, 16);
                shapeRenderer.end();

                // many things (tiles, fps, vector field, cost array)
                pathFindingSystem.debugRender(shapeRenderer, spriteBatch, font, camera);
                // more many things (turrets, targets)
                turretSystem.debugRender(shapeRenderer);
            }
        } catch (Exception ex) {
            System.err.println("exception while rendering screen");
            ex.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

        camera.zoom = mapHeight / ((float) height);

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
        tiledMapRenderer.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public void panCamera(float deltaX) {
        camera.position.x -= deltaX / 50;

        float effectiveWidth = camera.viewportWidth * camera.zoom;
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveWidth / 2f, mapWidth - effectiveWidth / 2f);

        camera.update();
    }

    public void debugTouch(int screenX, int screenY, int pointer, int button) {
        if (button == 1) {
            CoordinateUtil.touchToWorld(spawnPoint.set(screenX, screenY), camera);
            PathFindingSystem.TileType tileType = pathFindingSystem.getTile((int) spawnPoint.x, (int) spawnPoint.y);
            if (tileType == PathFindingSystem.TileType.EMPTY || tileType == PathFindingSystem.TileType.FLOOR) {
                pathFindingSystem.setTile((int) spawnPoint.x, (int) spawnPoint.y, PathFindingSystem.TileType.TOWER, true);
                Tower.newTower(spawnPoint.cpy());
            } else {
                System.out.println("can't place tower here: " + tileType);
            }
        }
    }

    public void toggleDebugRendering() {
        debugRendering = !debugRendering;
    }
}

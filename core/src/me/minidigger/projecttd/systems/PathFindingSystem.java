package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.VelocityComponent;
import me.minidigger.projecttd.utils.CoordinateUtil;
import me.minidigger.projecttd.utils.Vector2i;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathFindingSystem extends IteratingSystem {

    private final int mapWidth;
    private final int mapHeight;

    private final TileType[][] map;
    private int[][] cost;
    private boolean[][] visited;
    private Vector2[][] direction;

    private Vector2 goal;

    private ComponentMapper<VelocityComponent> velocityM = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<PathComponent> pathM = ComponentMapper.getFor(PathComponent.class);

    public PathFindingSystem(int mapHeight, int mapWidth, TiledMap tiledMap) {
        super(Family.all(PathComponent.class, TargetComponent.class, TransformComponent.class).get());

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;

        map = new TileType[mapWidth][mapHeight];

        TiledMapTileLayer path = (TiledMapTileLayer) tiledMap.getLayers().get("Path");
        TiledMapTileLayer rocks = (TiledMapTileLayer) tiledMap.getLayers().get("Rocks");
        TiledMapTileLayer bushes = (TiledMapTileLayer) tiledMap.getLayers().get("Bushes");

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (path.getCell(x, y) != null) {
                    map[x][y] = TileType.FLOOR;
                } else if (rocks.getCell(x, y) != null || bushes.getCell(x, y) != null) {
                    map[x][y] = TileType.WALL;
                } else {
                    map[x][y] = TileType.EMPTY;
                }
            }
        }
    }

    public void init(Vector2 goal) {
        goal = goal.set((int) goal.x, (int) goal.y);
        this.goal = goal; // save for later recalculation

        cost = new int[mapWidth][mapHeight];
        visited = new boolean[mapWidth][mapHeight];
        direction = new Vector2[mapWidth][mapHeight];

        // wavefront (bfs)
        Queue<Vector2i> queue = new LinkedList<>();
        visited[(int) goal.x][(int) goal.y] = true;
        queue.add(new Vector2i((int) goal.x, (int) goal.y));
        while (!queue.isEmpty()) {
            Vector2i node = queue.remove();
            for (Vector2i child : getNeighbors(node)) {
                if (child == null || visited[child.x][child.y]) continue;
                visited[child.x][child.y] = true;
                cost[child.x][child.y] = cost[node.x][node.y] + 1;
                queue.add(child);
            }
        }

        // vector field
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                // can't walk here (or goal), no point in having a dir
                if (map[x][y] != TileType.FLOOR || cost[x][y] == 0) {
                    direction[x][y] = new Vector2(0, 0);
                    continue;
                }
                boolean right = true, left = true, up = true, down = true;
                int leftX = x - 1, rightX = x + 1, upperY = y + 1, lowerY = y - 1;
                // check bounds
                if (x == 0) {
                    left = false;
                    leftX = x;
                }
                if (y == 0) {
                    down = false;
                    lowerY = y;
                }
                if (x == mapWidth - 1) {
                    right = false;
                    rightX = x;
                }
                if (y == mapHeight - 1) {
                    up = false;
                    upperY = y;
                }
                // check traversal
                if (map[leftX][y] != TileType.FLOOR) {
                    left = false;
                    leftX = x;
                }
                if (map[rightX][y] != TileType.FLOOR) {
                    right = false;
                    rightX = x;
                }
                if (map[x][upperY] != TileType.FLOOR) {
                    up = false;
                    upperY = y;
                }
                if (map[x][lowerY] != TileType.FLOOR) {
                    down = false;
                    lowerY = y;
                }

                int costUp = cost[x][upperY];
                int costDown = cost[x][lowerY];
                int costLeft = cost[leftX][y];
                int costRight = cost[rightX][y];

                // find right direction vector
                int min = up ? costUp : Integer.MAX_VALUE;
                Vector2 dir = new Vector2(0, 1);
                if (down && costDown < min) {
                    min = costDown;
                    dir.set(0, -1);
                }
                if (left && costLeft < min) {
                    min = costLeft;
                    dir.set(-1, 0);
                }
                if (right && costRight < min) {
                    //min = costRight;
                    dir.set(1, 0);
                }
                direction[x][y] = dir;
            }
        }
    }

    private Vector2i[] getNeighbors(Vector2i node) {
        Vector2i[] result = new Vector2i[4];
        if (node.x < mapWidth - 1 && !visited[node.x + 1][node.y] &&
                map[node.x + 1][node.y] == TileType.FLOOR) result[0] = new Vector2i(node.x + 1, node.y);
        if (node.y < mapHeight - 1 && !visited[node.x][node.y + 1] &&
                map[node.x][node.y + 1] == TileType.FLOOR) result[1] = new Vector2i(node.x, node.y + 1);
        if (node.x > 0 && !visited[node.x - 1][node.y] &&
                map[node.x - 1][node.y] == TileType.FLOOR) result[2] = new Vector2i(node.x - 1, node.y);
        if (node.y > 0 && !visited[node.x][node.y - 1] &&
                map[node.x][node.y - 1] == TileType.FLOOR) result[3] = new Vector2i(node.x, node.y - 1);
        return result;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformM.get(entity);
        PathComponent pathComponent = pathM.get(entity);

        // we still have a point to go to, no need to recalc it again
        if (pathComponent.nextPoint != null) {
            return;
        }

        int x = (int) transformComponent.position.x;
        int y = (int) transformComponent.position.y;

        // if goal reached, run action
        if (x == goal.x && y == goal.y) {
            pathComponent.nextPoint = null;
            Gdx.app.postRunnable(() -> pathComponent.completed.run(entity));
        }
        // if in bound, next point
        else if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
            pathComponent.nextPoint = new Vector2(x + 0.5f, y + 0.5f).add(direction[x][y]);
        }
        // do nothing
        else {
            pathComponent.nextPoint = null;
        }
    }

    public void debugRender(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Camera camera) {
        // box
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (float x = 0; x < mapWidth; x++) {
            for (float y = 0; y < mapHeight; y++) {
                shapeRenderer.box(x, y, 0, 1f, 1f, 0f);
            }
        }
        shapeRenderer.end();
        // fps
        spriteBatch.begin();
        font.setColor(Color.RED);
        font.draw(spriteBatch, Gdx.graphics.getFramesPerSecond() + "", 0, 15);
        font.setColor(Color.WHITE);
        // costs + cords
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Vector2 point = new Vector2(x, y + 1);
                CoordinateUtil.worldToTouch(point, camera);
                font.draw(spriteBatch, cost[x][y] + "", point.x, point.y);

//                point = new Vector2(x, y);
//                CoordinateUtil.worldToTouch(point, camera);
//                font.draw(spriteBatch, x + "", point.x, point.y);
//
//                point = new Vector2(x + 1, y);
//                CoordinateUtil.worldToTouch(point, camera);
//                font.draw(spriteBatch, y + "", point.x, point.y);
            }
        }
        spriteBatch.end();
        // vector
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Vector2 dir = direction[x][y];
                dir = dir.cpy().scl(0.5f);
                shapeRenderer.circle(x + 0.5f, y + 0.5f, 0.1f, 20);
                shapeRenderer.line(x + 0.5f, y + 0.5f, x + dir.x + 0.5f, y + dir.y + 0.5f);
            }
        }
        shapeRenderer.end();
    }

    public void setTile(int x, int y, TileType type, boolean recalc) {
        map[x][y] = type;
        if (recalc) {
            init(goal);
        }
    }

    public TileType getTile(int x, int y) {
        return map[x][y];
    }

    @FunctionalInterface
    public interface GoalReachedAction {

        void run(Entity entity);
    }

    public enum TileType {

        EMPTY,
        WALL,
        TOWER,
        FLOOR
    }
}

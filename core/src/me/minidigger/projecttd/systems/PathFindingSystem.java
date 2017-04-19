package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.pathfinding.FlatTiledNode;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathFindingSystem extends IteratingSystem {

    private final int mapWidth;
    private final int mapHeight;

    private final TileType[][] map;
    private final int[][] cost;
    private final int[][] direction;

    private ComponentMapper<PathComponent> pm = ComponentMapper.getFor(PathComponent.class);
    private ComponentMapper<TargetComponent> tm = ComponentMapper.getFor(TargetComponent.class);
    private ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);


    public PathFindingSystem(int mapHeight, int mapWidth, TiledMap tiledMap) {
        super(Family.all(PathComponent.class, TargetComponent.class, TransformComponent.class).get());

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;

        map = new TileType[mapWidth][mapHeight];
        cost = new int[mapWidth][mapHeight];
        direction = new int[mapWidth][mapHeight];

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
        Queue<Node> queue = new LinkedList<>();
        Node rootNode = new Node((int) goal.x, (int) goal.y);
        rootNode.visited = true;
        queue.add(rootNode);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            for (Node child : getNeighbors(node)) {
                if (child == null) continue;
                child.visited = true;
                child.cost = node.cost + 1;
                queue.add(child);
            }
        }
    }

    private Node[] getNeighbors(Node node) {
        Node[] result = new Node[8];
        // @formatter:off
        if (map[node.x + 1][node.y    ] == TileType.FLOOR) result[0] = new Node(node.x + 1, node.y);
        if (map[node.x + 1][node.y + 1] == TileType.FLOOR) result[1] = new Node(node.x + 1, node.y + 1);
        if (map[node.x    ][node.y + 1] == TileType.FLOOR) result[2] = new Node(node.x,node.y + 1);
        if (map[node.x - 1][node.y    ] == TileType.FLOOR) result[3] = new Node(node.x + 1, node.y);
        if (map[node.x - 1][node.y - 1] == TileType.FLOOR) result[4] = new Node(node.x + 1, node.y);
        if (map[node.x    ][node.y - 1] == TileType.FLOOR) result[5] = new Node(node.x + 1, node.y);
        if (map[node.x - 1][node.y + 1] == TileType.FLOOR) result[6] = new Node(node.x + 1, node.y);
        if (map[node.x + 1][node.y - 1] == TileType.FLOOR) result[7] = new Node(node.x + 1, node.y);
        // @formatter:on
        return result;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathComponent path = pm.get(entity);
        TransformComponent transform = transformM.get(entity);
        TargetComponent target = tm.get(entity);

        // no path yet or new path needed --> calc path
        if (path.path == null) {
            path.path = getPath(transform.position, target.target);
            if (path.path.getCount() == 0) return;
            // set first nextPoint
            FlatTiledNode node = path.path.get(path.index = 0);
            path.nextPoint.x = node.x + 0.5f;
            path.nextPoint.y = node.y + 0.5f;
            return;
        }

        // here yet?
        if (path.nextPoint.dst(transform.position) <= 0.5) {
            // don't go too far
            if (++path.index < path.path.getCount()) {
                FlatTiledNode node = path.path.get(path.index);
                path.nextPoint.x = node.x + 0.5f;
                path.nextPoint.y = node.y + 0.5f;
            } else {
                // goal reached
                path.completed.run(entity);
            }
        }
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
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

    public class Node {

        boolean visited = false;
        int x = 0;
        int y = 0;
        int cost = 0;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

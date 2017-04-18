package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.pathfinding.FlatTiledGraph;
import me.minidigger.projecttd.pathfinding.FlatTiledNode;
import me.minidigger.projecttd.pathfinding.FlatTiledSmoothableGraphPath;
import me.minidigger.projecttd.pathfinding.TileType;

import java.util.Arrays;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathFindingSystem extends IteratingSystem {

    private ComponentMapper<PathComponent> pm = ComponentMapper.getFor(PathComponent.class);
    private ComponentMapper<TargetComponent> tm = ComponentMapper.getFor(TargetComponent.class);
    private ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);

    private Heuristic<FlatTiledNode> manhattan = (node, endNode) -> Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    private FlatTiledGraph graph;
    private IndexedAStarPathFinder<FlatTiledNode> pathFinder;

    public PathFindingSystem(int mapHeight, int mapWidth, TiledMap tiledMap) {
        super(Family.all(PathComponent.class, TargetComponent.class, TransformComponent.class).get());

        graph = new FlatTiledGraph();

        TileType[][] map = new TileType[mapWidth][mapHeight];
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Path");
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (layer.getCell(x, y) != null) {
                    map[x][y] = TileType.FLOOR;
                } else {
                    map[x][y] = TileType.WALL;
                }
            }
        }

        graph.init(map, mapWidth, mapHeight);
        pathFinder = new IndexedAStarPathFinder<>(graph);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathComponent path = pm.get(entity);
        TransformComponent transform = transformM.get(entity);
        TargetComponent target = tm.get(entity);

        // no path yet, calc path
        if (path.path == null) {
            path.path = getPath(transform.position, target.target);
            // set first target
            FlatTiledNode node = path.path.get(0);
            target.target.x = node.x + 0.5f;
            target.target.y = node.y + 0.5f;
            return;
        }

        if (target.target.dst(transform.position) <= 0.5) {
            // don't go too far
            if (++path.index < path.path.getCount()) {
                FlatTiledNode node = path.path.get(path.index);
                target.target.x = node.x + 0.5f;
                target.target.y = node.y + 0.5f;
            }
        }
    }

    public FlatTiledSmoothableGraphPath getPath(Vector2 start, Vector2 stop) {
        FlatTiledSmoothableGraphPath path = new FlatTiledSmoothableGraphPath();
        FlatTiledNode startNode = graph.getNode((int) start.x, (int) start.y);
        FlatTiledNode endNode = graph.getNode((int) stop.x, (int) stop.y);
        System.out.println("calc path from " + startNode.x + "," + startNode.y + "(" + startNode.type + ") to " +
                +endNode.x + ", " + endNode.y + " (" + endNode.type + ")");
        if (pathFinder.searchNodePath(startNode, endNode, manhattan, path)) {
            System.out.println("found path " + path.getCount());
        } else {
            System.out.println("no path...");
        }
        return path;
    }

    public FlatTiledGraph getGraph() {
        return graph;
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        //DEBUG
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        for (int x = 0; x < graph.sizeX; x++) {
//            for (int y = 0; y < graph.sizeY; y++) {
//                graph.getNode(x, y).getConnections().forEach(s ->
//                        shapeRenderer.line(s.getFromNode().x + 0.5f, s.getFromNode().y + 0.5f,
//                                s.getToNode().x + 0.5f, s.getToNode().y + 0.5f));
//            }
//        }
//        shapeRenderer.end();
    }
}

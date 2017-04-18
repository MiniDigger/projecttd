package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.pathfinding.FlatTiledGraph;
import me.minidigger.projecttd.pathfinding.FlatTiledNode;
import me.minidigger.projecttd.pathfinding.FlatTiledRaycastCollisionDetector;
import me.minidigger.projecttd.pathfinding.FlatTiledSmoothableGraphPath;
import me.minidigger.projecttd.pathfinding.TileType;

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
    private PathSmoother<FlatTiledNode, Vector2> pathSmoother;

    public PathFindingSystem(int mapHeight, int mapWidth) {
        super(Family.all(PathComponent.class, TargetComponent.class, TransformComponent.class).get());

        graph = new FlatTiledGraph();

        TileType[][] map = new TileType[mapWidth][mapHeight];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                map[x][y] = TileType.FLOOR;
            }
        }

        graph.init(map, mapWidth, mapHeight);
        pathFinder = new IndexedAStarPathFinder<>(graph);
        pathSmoother = new PathSmoother<>(new FlatTiledRaycastCollisionDetector<>(graph));
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathComponent path = pm.get(entity);
        TransformComponent transform = transformM.get(entity);
        TargetComponent target = tm.get(entity);

        if (path.path == null) {
            System.out.println("calc path from " + transform.position + " to " + target.target);
            path.path = getPath(transform.position, target.target);
            return;
        }

        if (target.target.dst(transform.position) <= 0.5) {
            System.out.println("next node");
            FlatTiledNode node = path.path.get(path.index++);
            target.target.x = node.x;
            target.target.y = node.y;
        }
    }

    public FlatTiledSmoothableGraphPath getPath(Vector2 start, Vector2 stop) {
        FlatTiledSmoothableGraphPath path = new FlatTiledSmoothableGraphPath();
        pathFinder.searchNodePath(graph.getNode((int) start.x, (int) start.y), graph.getNode((int) stop.x, (int) stop.y), manhattan, path);
        pathSmoother.smoothPath(path);
        return path;
    }

    public FlatTiledGraph getGraph() {
        return graph;
    }
}

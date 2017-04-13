package me.minidigger.projecttd.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class Pathfinding {

    private Heuristic<FlatTiledNode> manhattan = (node, endNode) -> Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    private FlatTiledGraph graph;
    private IndexedAStarPathFinder<FlatTiledNode> pathFinder;
    private PathSmoother<FlatTiledNode, Vector2> pathSmoother;

    public void test() {
        graph = new FlatTiledGraph();
//        graph.init();
        pathFinder = new IndexedAStarPathFinder<>(graph);
        pathSmoother = new PathSmoother<>(new FlatTiledRaycastCollisionDetector<>(graph));
    }

    public FlatTiledSmoothableGraphPath getPath(Vector2 start, Vector2 stop) {
        FlatTiledSmoothableGraphPath path = new FlatTiledSmoothableGraphPath();
        pathFinder.searchNodePath(graph.getNode((int) start.x, (int) start.y), graph.getNode((int) stop.x, (int) stop.y), manhattan, path);
        pathSmoother.smoothPath(path);
        return path;
    }
}

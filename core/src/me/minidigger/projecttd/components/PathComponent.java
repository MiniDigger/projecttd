package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.minidigger.projecttd.pathfinding.FlatTiledSmoothableGraphPath;
import me.minidigger.projecttd.pathfinding.GoalReachedAction;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathComponent implements Component, Pool.Poolable {

    public FlatTiledSmoothableGraphPath path;
    public int index = 0;
    public Vector2 nextPoint = new Vector2();
    public GoalReachedAction completed = (e) -> {
    };
}

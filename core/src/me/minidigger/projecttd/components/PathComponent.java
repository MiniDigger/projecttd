package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import me.minidigger.projecttd.systems.PathFindingSystem;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathComponent implements Component, Pool.Poolable {

    public int tilesToGoal = -1;
    public Vector2 nextPoint;
    public PathFindingSystem.GoalReachedAction completed = (e) -> {
    };

    @Override
    public void reset() {
        tilesToGoal = -1;
        nextPoint = null;
        completed = (e) -> {
        };
    }
}

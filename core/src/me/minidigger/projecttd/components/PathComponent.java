package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import me.minidigger.projecttd.systems.PathFindingSystem;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathComponent implements Component, Pool.Poolable {

    public int index = 0;
    public Vector2 nextPoint = new Vector2();
    public PathFindingSystem.GoalReachedAction completed = (e) -> {
    };

    @Override
    public void reset() {
        index = 0;
        nextPoint = new Vector2();
        completed = (e) -> {
        };
    }
}

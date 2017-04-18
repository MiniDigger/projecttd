package me.minidigger.projecttd.pathfinding;

import com.badlogic.ashley.core.Entity;

/**
 * Created by mbenndorf on 18.04.2017.
 */
@FunctionalInterface
public interface GoalReachedAction {

    void run(Entity entity);
}

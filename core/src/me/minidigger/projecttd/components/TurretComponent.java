package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import me.minidigger.projecttd.systems.TurretSystem.TargetSelectionStrategy;

/**
 * Created by Martin on 29/04/2017.
 */
public class TurretComponent implements Component, Poolable {

    public Entity target;
    public float range = 2;
    public float attackSpeed = 1;
    public TargetSelectionStrategy strategy = TargetSelectionStrategy.FIRST;

    @Override
    public void reset() {
        target = null;
        range = 1;
        attackSpeed = 1;
        strategy = TargetSelectionStrategy.FIRST;
    }
}

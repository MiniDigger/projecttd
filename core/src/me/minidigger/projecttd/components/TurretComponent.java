package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Martin on 29/04/2017.
 */
public class TurretComponent implements Component, Poolable {

    public Entity target;
    public float range = 100;
    public float attackSpeed = 1;

    @Override
    public void reset() {
        target = null;
        range = 100;
        attackSpeed = 1;
    }
}

package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Martin on 03.04.2017.
 */
public class HealthComponent implements Component, Pool.Poolable {

    public double health = 100;

    @Override
    public void reset() {
        health = 100;
    }
}

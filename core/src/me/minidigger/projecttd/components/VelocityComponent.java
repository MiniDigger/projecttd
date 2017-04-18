package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Martin on 02.04.2017.
 */
public class VelocityComponent implements Component, Pool.Poolable {

    public Vector2 linear = new Vector2();
    public float angular = 0f;

    @Override
    public void reset() {
        linear = new Vector2();
        angular = 0f;
    }
}

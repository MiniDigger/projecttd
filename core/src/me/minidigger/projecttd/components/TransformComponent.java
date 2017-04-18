package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Martin on 02.04.2017.
 */
public class TransformComponent implements Component, Pool.Poolable {

    public Vector2 position;
    public float rotation = 0f;

    @Override
    public void reset() {
        position = new Vector2();
        rotation = 0f;
    }
}

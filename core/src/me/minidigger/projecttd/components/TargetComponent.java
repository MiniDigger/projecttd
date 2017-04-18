package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Martin on 07.04.2017.
 */
public class TargetComponent implements Component, Pool.Poolable {

    public Vector2 target = new Vector2();

    @Override
    public void reset() {
        target = new Vector2();
    }
}

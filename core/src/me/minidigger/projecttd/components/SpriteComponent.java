package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Martin on 02.04.2017.
 */
public class SpriteComponent implements Component, Pool.Poolable {

    public Sprite sprite;

    @Override
    public void reset() {
        sprite = null;
    }
}

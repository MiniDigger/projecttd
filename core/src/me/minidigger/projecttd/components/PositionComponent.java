package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Martin on 02.04.2017.
 */
public class PositionComponent implements Component {

    public float x = 0f;
    public float y = 0f;

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

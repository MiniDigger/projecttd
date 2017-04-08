package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Martin on 02.04.2017.
 */
public class TransformComponent implements Component {

    public Vector2 position;
    public float rotation = 0f;

    public TransformComponent(Vector2 spawn) {
        this.position = spawn;
    }
}

package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Martin on 07.04.2017.
 */
public class TargetComponent implements Component {

    public Vector2 target;

    public TargetComponent(Vector2 target) {
        this.target = target;
    }
}

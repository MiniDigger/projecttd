package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.entities.Minion;

/**
 * Created by mbenndorf on 28.04.2017.
 */
public class SpawnSystem extends IntervalSystem {

    private Engine engine;
    private float mapHeight;

    public SpawnSystem(Engine engine, float mapHeight, float interval) {
        super(interval);
        this.engine = engine;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void updateInterval() {
//        Entity minion = Minion.newMinion(new Vector2(1.5f, mapHeight - 0.5f - 5));
//        minion.getComponent(PathComponent.class).completed = (e) -> {
//            // potential race condition here, but who cares? this is debug code
//            engine.removeEntity(e);
//        };
    }
}

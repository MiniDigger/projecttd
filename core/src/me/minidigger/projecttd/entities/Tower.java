package me.minidigger.projecttd.entities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.*;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class Tower {

    public static PooledEngine ENGINE;
    public static Sprite SPRITE;

    private static ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private static ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public static Entity newTower(Vector2 spawn) {
        Entity entity = ENGINE.createEntity();

        // align
        spawn.x = (int) (spawn.x) + 0.5f;
        spawn.y = (int) (spawn.y) + 0.5f;

        entity.add(new SpriteComponent(SPRITE));
        entity.add(new TransformComponent(spawn));

        ENGINE.addEntity(entity);
        return entity;
    }

    public static SpriteComponent getSprite(Entity minion) {
        return sm.get(minion);
    }

    public static TransformComponent getTransform(Entity minion) {
        return tm.get(minion);
    }
}

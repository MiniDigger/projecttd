package me.minidigger.projecttd.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.components.HealthComponent;
import me.minidigger.projecttd.components.SpriteComponent;
import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.VelocityComponent;

/**
 * Created by Martin on 07.04.2017.
 */
public class Minion {

    public static PooledEngine ENGINE;
    public static Sprite SPRITE;

    public static Entity newMinion(Vector2 spawn, Vector2 target) {
        Entity entity = ENGINE.createEntity();

        entity.add(new SpriteComponent(SPRITE));
        entity.add(new VelocityComponent());
        entity.add(new TransformComponent(spawn));
        entity.add(new TargetComponent(target));
        entity.add(new HealthComponent(100));

        ENGINE.addEntity(entity);
        return entity;
    }
}

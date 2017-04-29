package me.minidigger.projecttd.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.*;

/**
 * Created by Martin on 07.04.2017.
 */
public class Minion {

    public static PooledEngine ENGINE;
    public static Sprite SPRITE;

    public static Entity newMinion(Vector2 spawn) {
        Entity entity = ENGINE.createEntity();

        SpriteComponent spriteComponent = ENGINE.createComponent(SpriteComponent.class);
        spriteComponent.sprite = SPRITE;
        entity.add(spriteComponent);

        VelocityComponent velocityComponent = ENGINE.createComponent(VelocityComponent.class);
        entity.add(velocityComponent);

        TransformComponent transformComponent = ENGINE.createComponent(TransformComponent.class);
        transformComponent.position = spawn;
        entity.add(transformComponent);

        HealthComponent healthComponent = ENGINE.createComponent(HealthComponent.class);
        healthComponent.health = 100;
        entity.add(healthComponent);

        PathComponent pathComponent = ENGINE.createComponent(PathComponent.class);
        entity.add(pathComponent);

        ENGINE.addEntity(entity);
        return entity;
    }
}

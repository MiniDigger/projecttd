package me.minidigger.projecttd.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.SpriteComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.TurretComponent;
import me.minidigger.projecttd.systems.TurretSystem;
import me.minidigger.projecttd.utils.CoordinateUtil;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class Tower {

    public static PooledEngine ENGINE;
    public static Sprite SPRITE;

    public static Entity newTower(Vector2 spawn) {
        Entity entity = ENGINE.createEntity();

        CoordinateUtil.alignToGrid(spawn);

        SpriteComponent spriteComponent = ENGINE.createComponent(SpriteComponent.class);
        spriteComponent.sprite = SPRITE;
        entity.add(spriteComponent);

        TransformComponent transformComponent = ENGINE.createComponent(TransformComponent.class);
        transformComponent.position = spawn;
        entity.add(transformComponent);

        TurretComponent turretComponent = ENGINE.createComponent(TurretComponent.class);
        entity.add(turretComponent);

        ENGINE.addEntity(entity);
        return entity;
    }
}

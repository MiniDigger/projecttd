package me.minidigger.projecttd.entities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.components.HealthComponent;
import me.minidigger.projecttd.components.PathComponent;
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

    private static ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private static ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private static ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private static ComponentMapper<TargetComponent> tartgetM = ComponentMapper.getFor(TargetComponent.class);
    private static ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public static Entity newMinion(Vector2 spawn, Vector2 target) {
        Entity entity = ENGINE.createEntity();

        SpriteComponent spriteComponent = ENGINE.createComponent(SpriteComponent.class);
        spriteComponent.sprite = SPRITE;
        entity.add(spriteComponent);

        VelocityComponent velocityComponent = ENGINE.createComponent(VelocityComponent.class);
        entity.add(velocityComponent);

        TransformComponent transformComponent = ENGINE.createComponent(TransformComponent.class);
        transformComponent.position = spawn;
        entity.add(transformComponent);

        TargetComponent targetComponent = ENGINE.createComponent(TargetComponent.class);
        targetComponent.target = target;
        entity.add(targetComponent);

        HealthComponent healthComponent = ENGINE.createComponent(HealthComponent.class);
        healthComponent.health = 100;
        entity.add(healthComponent);

        PathComponent pathComponent = ENGINE.createComponent(PathComponent.class);
        entity.add(pathComponent);

        ENGINE.addEntity(entity);
        return entity;
    }

    public static SpriteComponent getSprite(Entity minion) {
        return sm.get(minion);
    }

    public static VelocityComponent getVelocity(Entity minion) {
        return vm.get(minion);
    }

    public static TransformComponent getTransform(Entity minion) {
        return tm.get(minion);
    }

    public static TargetComponent getTarget(Entity minion) {
        return tartgetM.get(minion);
    }

    public static HealthComponent getHealth(Entity minion) {
        return hm.get(minion);
    }
}

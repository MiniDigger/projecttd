package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.VelocityComponent;

/**
 * Created by Martin on 02.04.2017.
 */
public class MovementSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);

        transform.position.x += velocity.linear.x * deltaTime;
        transform.position.y += velocity.linear.y * deltaTime;

        transform.rotation += velocity.angular * deltaTime;
    }
}
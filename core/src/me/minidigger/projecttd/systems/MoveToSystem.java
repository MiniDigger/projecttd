package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.components.TargetComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.VelocityComponent;

/**
 * Created by Martin on 07.04.2017.
 */
public class MoveToSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<TargetComponent> tm = ComponentMapper.getFor(TargetComponent.class);

    private Vector2 temp = new Vector2();

    private float accelerationTime = 0.1f;
    private float maxAcceleration = 3f;
    private float maxSpeed = 6f;

    public MoveToSystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class, TargetComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        TargetComponent target = tm.get(entity);

        if (target.target == null) {
            velocity.linear.setZero();
            velocity.angular = 0;
            return;
        }

        // calc dir and len
        Vector2 toTarget = temp.set(target.target).sub(transform.position);
        float distance = toTarget.len();
        // don't go too far!
        if (distance <= 0.05) {
            velocity.linear.setZero();
            velocity.angular = 0;
            return;
        }

        // Target velocity combines speed and direction
        Vector2 targetVelocity = toTarget.scl(maxSpeed / distance); // Optimized code for: toTarget.nor().scl(targetSpeed)

        // Acceleration tries to get to the target velocity without exceeding max acceleration
        // Notice that steering.linear and targetVelocity are the same vector
        targetVelocity.sub(velocity.linear).scl(1f / accelerationTime).limit(maxAcceleration);

        velocity.linear.set(toTarget);
    }
}

package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.utils.ArithmeticUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import me.minidigger.projecttd.components.MinionComponent;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.VelocityComponent;
import me.minidigger.projecttd.entities.Minion;
import me.minidigger.projecttd.utils.VectorUtil;

/**
 * Created by Martin on 07.04.2017.
 */
public class MoveToSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PathComponent> pathM = ComponentMapper.getFor(PathComponent.class);
    private ComponentMapper<MinionComponent> minionM = ComponentMapper.getFor(MinionComponent.class);

    private Vector2 temp = new Vector2();

    private float linearAccelerationTime = 0.1f;
    private float maxLinearAcceleration = 3f;

    private float zeroThreshold = 0.001f;

    private float maxAngularAcceleration = 300f;
    private float maxAngularSpeed = 400f;
    private float angularAccelerationTime = 0.1f;

    public MoveToSystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class, PathComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        PathComponent path = pathM.get(entity);
        MinionComponent minionComponent = minionM.get(entity);

        if (path.nextPoint == null) {
            velocity.linear.setZero();
            velocity.angular = 0;
            return;
        }

        // calc dir and len
        Vector2 toTarget = temp.set(path.nextPoint).sub(transform.position);
        float distance = toTarget.len();
        // don't go too far!
        if (distance <= 0.05) {
            velocity.linear.setZero();
            velocity.angular = 0;
            path.nextPoint = null;
            return;
        }

        float maxSpeed = minionComponent.speed;
        System.out.println("speed " + maxSpeed);
        // Target velocity combines speed and direction
        Vector2 targetVelocity = toTarget.scl(maxSpeed / distance); // Optimized code for:
        // toTarget.nor().scl(maxSpeed)

        // Acceleration tries to get to the nextPoint velocity without exceeding max acceleration
        targetVelocity.sub(velocity.linear).scl(1f / linearAccelerationTime).limit(maxLinearAcceleration);

        // set it
        velocity.linear.set(toTarget);

        // angular

        // Check for a zero direction, and set to 0 is so
        if (velocity.linear.isZero(zeroThreshold)) {
            velocity.angular = 0;
            return;
        }

        // Calculate the orientation based on the velocity of the owner
        float targetOrientation = VectorUtil.vectorToAngle(velocity.linear);

        // Get the rotation direction to the nextPoint wrapped to the range [-PI, PI]
        float rotation = ArithmeticUtils.wrapAngleAroundZero(targetOrientation - (transform.rotation - 90) * MathUtils.degreesToRadians);

        // Absolute rotation
        float rotationSize = rotation < 0f ? -rotation : rotation;

        // Check if we are there, set velocity to 0 and return if so
        if (rotationSize <= 0.1) {
            velocity.angular = 0;
            return;
        }

        // Use maximum rotation
        float targetRotation = maxAngularSpeed;

        // The final nextPoint rotation combines
        // speed (already in the variable) and direction
        targetRotation *= rotation / rotationSize;

        // Acceleration tries to get to the nextPoint rotation
        velocity.angular = (targetRotation - velocity.angular) / angularAccelerationTime;

        // Check if the absolute acceleration is too great
        float angularAcceleration = velocity.angular < 0f ? -velocity.angular : velocity.angular;
        if (angularAcceleration > maxAngularAcceleration) {
            velocity.angular *= maxAngularAcceleration / angularAcceleration;
        }
    }
}

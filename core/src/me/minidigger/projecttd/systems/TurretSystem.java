package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.minidigger.projecttd.components.HealthComponent;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.TransformComponent;
import me.minidigger.projecttd.components.TurretComponent;
import me.minidigger.projecttd.utils.Pair;

/**
 * Created by Martin on 29/04/2017.
 */
public class TurretSystem extends IteratingSystem {

    private ComponentMapper<TurretComponent> turretM = ComponentMapper.getFor(TurretComponent.class);
    private ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);

    private static ComponentMapper<PathComponent> pathM = ComponentMapper.getFor(PathComponent.class);
    private static ComponentMapper<HealthComponent> healthM = ComponentMapper.getFor(HealthComponent.class);

    private Family targetFamily = Family.all(HealthComponent.class).get();// TODO better target selection
    private ImmutableArray<Entity> targets;

    public TurretSystem() {
        super(Family.all(TurretComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        targets = engine.getEntitiesFor(targetFamily);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        targets = null;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TurretComponent turretComponent = turretM.get(entity);
        TransformComponent turretTransform = transformM.get(entity);

        // target selection
        Entity currentFav = null;
        float currentFavData = -1;
        for (Entity target : targets) {
            TransformComponent targetTransform = transformM.get(target);
            float dst = turretTransform.position.dst2(targetTransform.position);
            if (dst <= (turretComponent.range * turretComponent.range)) {
                Pair<Entity, Float> result = turretComponent.strategy.calculate(currentFav, currentFavData, dst, target);
                currentFav = result.a;
                currentFavData = result.b;
            }
        }

        if (currentFav != null) {
            turretComponent.target = currentFav;
        }

        // shooting
        //TODO shooting with attack speed and stuff
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        // range
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        for (Entity turret : getEntities()) {
            TurretComponent turretComponent = turretM.get(turret);
            TransformComponent transformComponent = transformM.get(turret);

            shapeRenderer.circle(transformComponent.position.x, transformComponent.position.y, turretComponent.range, 20);
        }
        shapeRenderer.end();

        // targets
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        for (Entity turret : getEntities()) {
            TurretComponent turretComponent = turretM.get(turret);

            if (turretComponent.target != null) {
                TransformComponent targetTransform = transformM.get(turretComponent.target);
                shapeRenderer.circle(targetTransform.position.x, targetTransform.position.y, 0.1f, 10);
            }
        }
        shapeRenderer.end();

    }

    public interface TargetSelectionStrategy {
        TargetSelectionStrategy NEAREST = (current, data, dst, target) -> {
            if (data == -1 || dst < data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        TargetSelectionStrategy FURTHEST = (current, data, dst, target) -> {
            if (data == -1 || dst > data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        TargetSelectionStrategy FIRST = (current, data, dst, target) -> {
            PathComponent pathComponent = pathM.get(target);
            if (data == -1 || pathComponent.tilesToGoal < data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        TargetSelectionStrategy LAST = (current, data, dst, target) -> {
            PathComponent pathComponent = pathM.get(target);
            if (data == -1 || pathComponent.tilesToGoal > data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        TargetSelectionStrategy MAX_HEALTH = (current, data, dst, target) -> {
            HealthComponent healthComponent = healthM.get(target);
            if (data == -1 || healthComponent.health > data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        TargetSelectionStrategy MIN_HEALTH = (current, data, dst, target) -> {
            HealthComponent healthComponent = healthM.get(target);
            if (data == -1 || healthComponent.health > data) {
                return new Pair<>(target, dst);
            }
            return new Pair<>(current, data);
        };

        Pair<Entity, Float> calculate(Entity currentFav, float currentFavData, float dst, Entity target);
    }
}

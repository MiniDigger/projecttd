package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.minidigger.projecttd.components.TurretComponent;

/**
 * Created by Martin on 29/04/2017.
 */
public class TurretSystem extends IteratingSystem {

    private ComponentMapper<TurretComponent> turretM = ComponentMapper.getFor(TurretComponent.class);

    public TurretSystem() {
        super(Family.all(TurretComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TurretComponent turretComponent = turretM.get(entity);

        // we already got an target
        if(turretComponent.target != null){
            return;
        }
    }
}

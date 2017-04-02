package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import me.minidigger.projecttd.components.PositionComponent;
import me.minidigger.projecttd.components.RotationComponent;
import me.minidigger.projecttd.components.SpriteComponent;

/**
 * Created by Martin on 02.04.2017.
 */
public class RenderSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Camera camera;

    private ComponentMapper<SpriteComponent> spriteM;
    private ComponentMapper<PositionComponent> positionM;
    private ComponentMapper<RotationComponent> rotationM;

    public RenderSystem(Camera camera) {
        super(Family.all(SpriteComponent.class).get());

        this.camera = camera;
        renderQueue = new Array<>();
        batch = new SpriteBatch();

        spriteM = ComponentMapper.getFor(SpriteComponent.class);
        positionM = ComponentMapper.getFor(PositionComponent.class);
        rotationM = ComponentMapper.getFor(RotationComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        System.out.println("add entity to process");
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            SpriteComponent sprite = spriteM.get(entity);
            PositionComponent position = positionM.get(entity);
            RotationComponent rotation = rotationM.get(entity);

            sprite.sprite.setRotation(rotation.degrees);
            sprite.sprite.setPosition(position.x, position.y);
            System.out.println("sprite.sprite.getX() = " + sprite.sprite.getX());

            sprite.sprite.draw(batch);
        }
        batch.end();
    }
}

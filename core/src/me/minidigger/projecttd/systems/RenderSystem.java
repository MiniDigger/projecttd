package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import me.minidigger.projecttd.components.SpriteComponent;
import me.minidigger.projecttd.components.TransformComponent;

/**
 * Created by Martin on 02.04.2017.
 */
public class RenderSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Camera camera;

    private ComponentMapper<SpriteComponent> spriteM;
    private ComponentMapper<TransformComponent> positionM;

    private float tilewidth;

    public RenderSystem(Camera camera, int tilewidth) {
        super(Family.all(SpriteComponent.class, TransformComponent.class).get());

        this.tilewidth = tilewidth / 2f;

        this.camera = camera;
        renderQueue = new Array<>();
        batch = new SpriteBatch();

        spriteM = ComponentMapper.getFor(SpriteComponent.class);
        positionM = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            SpriteComponent sprite = spriteM.get(entity);
            TransformComponent transform = positionM.get(entity);

            sprite.sprite.setRotation(transform.rotation);
            sprite.sprite.setPosition(transform.position.x - tilewidth, transform.position.y - tilewidth);

            sprite.sprite.draw(batch);
        }
        batch.end();
    }
}

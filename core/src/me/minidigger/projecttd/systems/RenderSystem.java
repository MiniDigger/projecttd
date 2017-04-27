package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.minidigger.projecttd.components.SpriteComponent;
import me.minidigger.projecttd.components.TransformComponent;

/**
 * Created by Martin on 02.04.2017.
 */
public class RenderSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Camera camera;

    private ComponentMapper<SpriteComponent> spriteM;
    private ComponentMapper<TransformComponent> positionM;

    private float tileWidth;

    public RenderSystem(Camera camera, int tileWidth) {
        super(Family.all(SpriteComponent.class, TransformComponent.class).get());

        this.tileWidth = tileWidth / 2f;

        this.camera = camera;
        batch = new SpriteBatch();

        spriteM = ComponentMapper.getFor(SpriteComponent.class);
        positionM = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // ignore
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : getEntities()) {
            SpriteComponent sprite = spriteM.get(entity);
            TransformComponent transform = positionM.get(entity);

            sprite.sprite.setRotation(transform.rotation);
            sprite.sprite.setPosition(transform.position.x - tileWidth, transform.position.y - tileWidth);

            sprite.sprite.draw(batch);
        }
        batch.end();
    }
}

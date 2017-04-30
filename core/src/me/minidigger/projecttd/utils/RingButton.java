package me.minidigger.projecttd.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Martin on 30/04/2017.
 */
public class RingButton extends Actor {

    private ShapeRenderer renderer;
    private ArcRenderer arcRenderer;

    private float radius = 2f;


    public RingButton(ShapeRenderer shapeRenderer, ArcRenderer arcRenderer) {
        renderer = shapeRenderer;
        this.arcRenderer = arcRenderer;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(true) return;//TODO implement ring button
        arcRenderer.begin(ArcRenderer.ShapeType.Filled);
        arcRenderer.setColor(getColor());

        float originX = getX();
        float originY = getY();

        // part 1
        arcRenderer.arc(originX - 0.1f, originY + radius, radius, 90, 180, 400);

        // part 2
        arcRenderer.arc(originX + 0.1f, originY + radius, radius, -90, 180, 400);

        // middle
        arcRenderer.setColor(Color.RED);
        arcRenderer.arc(originX, originY + radius, radius / 3f, 0, 360, 800);

        arcRenderer.end();
    }
}

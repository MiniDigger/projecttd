package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import me.minidigger.projecttd.entities.Minion;

/**
 * Created by Martin on 13/05/2017.
 */
public class WaveGroupBuilder {

    private WaveGroup group = new WaveGroup();
    private WaveBuilder builder;

    WaveGroupBuilder(WaveBuilder builder) {
        this.builder = builder;
    }

    public WaveGroupBuilder delay(float delay) {
        group.setDelay(delay);
        return this;
    }

    public WaveGroupBuilder interval(float interval) {
        group.setInterval(interval);
        return this;
    }

    public WaveGroupBuilder count(int count) {
        group.setCount(count);
        return this;
    }

    public WaveGroupBuilder health(float health) {
        group.setHealth(health);
        return this;
    }

    public WaveGroupBuilder speed(float speed) {
        group.setSpeed(speed);
        return this;
    }

    public WaveGroupBuilder type(Minion.MinionType type) {
        group.setType(type);
        return this;
    }

    public WaveGroupBuilder sprite(Sprite sprite) {
        group.setSprite(sprite);
        return this;
    }

    public WaveBuilder finish() {
        builder.group(group);
        return builder;
    }
}

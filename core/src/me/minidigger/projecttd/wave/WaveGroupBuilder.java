package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.google.common.base.Preconditions;
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

    public WaveGroupBuilder spawn(Vector2 spawn) {
        group.setSpawn(spawn);
        return this;
    }

    public WaveGroupBuilder goal(Vector2 goal) {
        group.setGoal(goal);
        return this;
    }

    public WaveGroupBuilder money(int money) {
        group.setMoney(money);
        return this;
    }

    public WaveGroupBuilder points(int points) {
        group.setPoints(points);
        return this;
    }

    public WaveBuilder finish() {
        Preconditions.checkNotNull(group.getSpawn(), "Group must have a spawn");
        Preconditions.checkNotNull(group.getGoal(), "Group must have a goal");

        builder.group(group);
        return builder;
    }
}

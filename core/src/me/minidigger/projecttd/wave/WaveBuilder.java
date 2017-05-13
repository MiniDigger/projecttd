package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import me.minidigger.projecttd.entities.Minion;

/**
 * Created by Martin on 01/05/2017.
 */
public class WaveBuilder {

    private Wave wave = new Wave();
    private WaveGroup group;

    public WaveBuilder group() {
        if (group == null) {
            group = new WaveGroup();
        } else {
            wave.addGroup(group);
            group = new WaveGroup();
        }
        return this;
    }

    public WaveBuilder delay(float delay) {
        ensureGroupCreated();
        group.setDelay(delay);
        return this;
    }

    public WaveBuilder interval(float interval) {
        ensureGroupCreated();
        group.setInterval(interval);
        return this;
    }

    public WaveBuilder count(int count) {
        ensureGroupCreated();
        group.setCount(count);
        return this;
    }

    public WaveBuilder health(float health) {
        ensureGroupCreated();
        group.setHealth(health);
        return this;
    }

    public WaveBuilder speed(float speed) {
        ensureGroupCreated();
        group.setSpeed(speed);
        return this;
    }

    public WaveBuilder type(Minion.MinionType type) {
        ensureGroupCreated();
        group.setType(type);
        return this;
    }

    public WaveBuilder sprite(Sprite sprite) {
        ensureGroupCreated();
        group.setSprite(sprite);
        return this;
    }

    public WaveBuilder name(String name) {
        wave.setName(name);
        return this;
    }

    public WaveBuilder waveType(WaveType type) {
        wave.setType(type);
        return this;
    }

    public WaveBuilder points(int points){
        wave.setPoints(points);
        return this;
    }

    public WaveBuilder money(float amount){
        wave.setMoney(amount);
        return this;
    }

    public Wave build() {
        if (group != null) {
            wave.addGroup(group);
        }
        return wave;
    }

    private void ensureGroupCreated() {
        if (group == null) {
            throw new IllegalStateException("Need to create a new group using #group() first!");
        }
    }

    public void test() {
        Sprite minionSprite = null, smallTankSprite = null, bigTankSprite = null;
        new WaveBuilder().name("Wave 1").waveType(WaveType.NORMAL).points(100).money(10)
                .group().delay(5).interval(1).health(100).speed(1).type(Minion.MinionType.LAND).count(10).sprite(minionSprite)
                .group().delay(20).interval(2).health(300).speed(0.5f).type(Minion.MinionType.LAND).count(5).sprite(smallTankSprite)
                .group().delay(30).interval(1).health(1000).speed(0.3f).type(Minion.MinionType.LAND).count(1).sprite(bigTankSprite)
                .build();
    }
}

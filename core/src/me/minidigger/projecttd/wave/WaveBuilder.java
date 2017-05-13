package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import me.minidigger.projecttd.entities.Minion;

import static com.google.common.base.Preconditions.*;

/**
 * Created by Martin on 01/05/2017.
 */
public class WaveBuilder {

    private Wave wave = new Wave();

    public WaveGroupBuilder group() {
        return new WaveGroupBuilder(this);
    }

    public WaveBuilder name(String name) {
        wave.setName(name);
        return this;
    }

    public WaveBuilder waveType(WaveType type) {
        wave.setType(type);
        return this;
    }

    public WaveBuilder points(int points) {
        wave.setPoints(points);
        return this;
    }

    public WaveBuilder money(float amount) {
        wave.setMoney(amount);
        return this;
    }

    public WaveBuilder group(WaveGroup group) {
        wave.addGroup(group);
        return this;
    }

    public Wave build() {
        checkNotNull(wave.getName(), "You need to specify a name for the wave using name()");
        checkNotNull(wave.getGroups(), "You need to specify at least one group for this wave using group()");

        return wave;
    }

    public void test() {
        Sprite minionSprite = null, smallTankSprite = null, bigTankSprite = null;
        new WaveBuilder().name("Wave 1").waveType(WaveType.NORMAL).points(100).money(10)
                .group().delay(5).interval(1).health(100).speed(1).type(Minion.MinionType.LAND).count(10).sprite(minionSprite).finish()
                .group().delay(20).interval(2).health(300).speed(0.5f).type(Minion.MinionType.LAND).count(5).sprite(smallTankSprite).finish()
                .group().delay(30).interval(1).health(1000).speed(0.3f).type(Minion.MinionType.LAND).count(1).sprite(bigTankSprite).finish()
                .build();
    }
}

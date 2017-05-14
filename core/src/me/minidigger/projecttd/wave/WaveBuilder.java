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
}

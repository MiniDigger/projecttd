package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import me.minidigger.projecttd.entities.Minion;

/**
 * Created by Martin on 01/05/2017.
 */
public class WaveBuilder {

    public WaveBuilder group() {
        return this;
    }

    public WaveBuilder delay(float delay) {
        return this;
    }

    public WaveBuilder interval(float interval) {
        return this;
    }

    public WaveBuilder count(int count) {
        return this;
    }

    public WaveBuilder health(float health) {
        return this;
    }

    public WaveBuilder speed(float speed) {
        return this;
    }

    public WaveBuilder type(Minion.MinionType type) {
        return this;
    }

    public WaveBuilder sprite(Sprite sprite) {
        return this;
    }

    public Wave build(){
        return new Wave();
    }

    public void test(){
//        new WaveBuilder()
//                .group().delay(5).interval(1).health(100).speed(1).type(Minion.MinionType.LAND).count(10).sprite(minionSprite)
//                .group().delay(20).interval(2).health(300).speed(0.5f).type(Minion.MinionType.LAND).count(5).sprite(smallTankSprite)
//                .group().delay(30).interval(1).health(1000).speed(0.3f).type(Minion.MinionType.LAND).count(1).sprite(bigTankSprite)
//                .build();
    }
}

package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import me.minidigger.projecttd.entities.Minion;

/**
 * Created by Martin on 01/05/2017.
 */
public class MinionComponent implements Component, Pool.Poolable {

    public int money = 1;
    public float speed = 1f;
    public Minion.MinionType type = Minion.MinionType.LAND;

    @Override
    public void reset() {
        money = 1;
        speed = 1f;
        type = Minion.MinionType.LAND;
    }
}

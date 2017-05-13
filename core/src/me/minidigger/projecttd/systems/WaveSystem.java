package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import me.minidigger.projecttd.entities.Minion;
import me.minidigger.projecttd.level.Level;
import me.minidigger.projecttd.wave.Wave;
import me.minidigger.projecttd.wave.WaveGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 13/05/2017.
 */
public class WaveSystem extends EntitySystem {

    private Level level;
    //TODO detect when wave is over and next one should start

    private Wave activeWave;
    private Map<Integer, WaveGroup> groups = new HashMap<>();
    private Map<Integer, Float> intervals = new HashMap<>();
    private Map<Integer, Integer> counts = new HashMap<>();
    private Map<Integer, Float> accumulator = new HashMap<>();
    private Map<Integer, Status> statusMap = new HashMap<>();

    public WaveSystem(Level level) {
        this.level = level;
        if (level.getWaves().size() > 0) {
            setActiveWave(level.getWaves().get(0));
        }
    }

    public void setActiveWave(Wave activeWave) {
        this.activeWave = activeWave;

        groups = new HashMap<>();
        intervals = new HashMap<>();
        counts = new HashMap<>();
        accumulator = new HashMap<>();
        statusMap = new HashMap<>();

        for (int i = 0; i < activeWave.getGroups().size(); i++) {
            WaveGroup group = activeWave.getGroups().get(i);

            groups.put(i, group);
            intervals.put(i, group.getDelay());
            counts.put(i, group.getCount());
            accumulator.put(i, 0f);
            statusMap.put(i, Status.INITIAL_DELAY);
        }
    }

    @Override
    public void update(float deltaTime) {
        if (activeWave == null) {
            return;
        }

        for (int id : groups.keySet()) {
            accumulator.computeIfPresent(id, (i, a) -> {
                a += deltaTime;
                float interval = intervals.get(i);
                while (a >= interval) {
                    a -= interval;
                    updateWave(i);
                }
                intervals.put(i, interval);
                return a;
            });
        }
    }

    private void updateWave(int id) {
        WaveGroup group = groups.get(id);
        switch (statusMap.get(id)) {
            case INITIAL_DELAY:
                Gdx.app.log("DEBUG", "Group " + id + ": Initial interval over");
                statusMap.put(id, Status.RUNNING);
                intervals.put(id, group.getInterval());
            case RUNNING:
                int count = counts.get(id);
                Gdx.app.log("DEBUG", "Group " + id + ": Spawn, " + count + " more to go!");
                if (--count <= 0) {
                    statusMap.put(id, Status.ENDED);
                    counts.put(id, 1000);
                } else {
                    counts.put(id, count);
                }
                spawn(group.getHealth(), group.getSpeed(), group.getSprite(), group.getType());
                break;
            case ENDED:
                Gdx.app.log("DEBUG", "Group " + id + ": Its over");
                // ignore
                break;
        }
    }

    private void spawn(float health, float speed, Sprite sprite, Minion.MinionType type) {
        Gdx.app.log("DEBUG", "Spawn " + health + " " + speed + " " + type);
    }

    enum Status {
        INITIAL_DELAY, RUNNING, ENDED
    }
}

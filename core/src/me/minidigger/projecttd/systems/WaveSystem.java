package me.minidigger.projecttd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.components.HealthComponent;
import me.minidigger.projecttd.components.MinionComponent;
import me.minidigger.projecttd.components.PathComponent;
import me.minidigger.projecttd.components.SpriteComponent;
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
    private int totalEntities = 0;
    private int deadEntities = 0;
    private int progress = 0;
    private Map<Integer, WaveGroup> groups = new HashMap<>();
    private Map<Integer, Float> intervals = new HashMap<>();
    private Map<Integer, Integer> counts = new HashMap<>();
    private Map<Integer, Float> accumulator = new HashMap<>();
    private Map<Integer, Status> statusMap = new HashMap<>();

    private ComponentMapper<HealthComponent> healthM = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<MinionComponent> minionM = ComponentMapper.getFor(MinionComponent.class);
    private ComponentMapper<SpriteComponent> spriteM = ComponentMapper.getFor(SpriteComponent.class);

    public WaveSystem(Level level) {
        this.level = level;
        if (level.getWaves().size() > 0) {
            setActiveWave(level.getWaves().get(0));
        }
    }

    public void setActiveWave(Wave activeWave) {
        this.activeWave = activeWave;

        totalEntities = 0;
        deadEntities = 0;
        progress = 0;

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

            totalEntities += group.getCount();
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
                spawn(group.getHealth(), group.getSpeed(), group.getMoney(), group.getPoints(), group.getSprite(), group.getType(), group.getSpawn(), group.getGoal());
                break;
            case ENDED:
                Gdx.app.log("DEBUG", "Group " + id + ": Its over");
                // ignore
                break;
        }
    }

    private void spawn(float health, float speed, int money, int points, Sprite sprite, Minion.MinionType type, Vector2 spawn, Vector2 goal) {
        Gdx.app.log("DEBUG", "Spawn " + health + " " + speed + " " + type + " " + spawn);
        Entity minion = Minion.newMinion(spawn.cpy());
        healthM.get(minion).health = health;
        healthM.get(minion).deathTrigger = () -> {
            deadEntities++;
            Gdx.app.log("DEBUG", "Progress: " + progress + "/" + totalEntities + "spawned " + deadEntities + "/" + totalEntities + " dead");
        };

        minionM.get(minion).money = money;
        minionM.get(minion).points = points;
        minionM.get(minion).type = type;
        minionM.get(minion).speed = speed;

        if (sprite == null) {
            //TODO DEBUG
            Gdx.app.log("DEBUG", "No sprite?!");
        } else {
            spriteM.get(minion).sprite = sprite;
        }
        Gdx.app.log("DEBUG", "SPAWNED");

        //TODO do something with the goal

        //TODO find a better way of doing such hooks (guava event bus?)
        minion.getComponent(PathComponent.class).completed = (e) -> {
            // potential race condition here, but who cares? this is debug code
            Gdx.app.log("DEBUG", "ONE LIVE LOST!");
            deadEntities++;
            Minion.ENGINE.removeEntity(e);
            Gdx.app.log("DEBUG", "Progress: " + progress + "/" + totalEntities + "spawned " + deadEntities + "/" + totalEntities + " dead");
        };

        progress++;

        Gdx.app.log("DEBUG", "Progress: " + progress + "/" + totalEntities + "spawned " + deadEntities + "/" + totalEntities + " dead");
    }

    enum Status {
        INITIAL_DELAY, RUNNING, ENDED
    }
}

package me.minidigger.projecttd.wave;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.math.Vector2;
import me.minidigger.projecttd.entities.Minion.MinionType;

public class WaveGroup {

    private float delay = 0;
    private float interval = 0;
    private float health = 0;
    private float speed = 0;
    private MinionType type = MinionType.LAND;
    private int count = 0;
    private Sprite sprite = null;
    private Vector2 spawn;
    private Vector2 goal;
    private int points;
    private int money;

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public MinionType getType() {
        return type;
    }

    public void setType(MinionType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSpawn(Vector2 spawn) {
        this.spawn = spawn;
    }

    public Vector2 getSpawn() {
        return spawn;
    }

    public void setGoal(Vector2 goal) {
        this.goal = goal;
    }

    public Vector2 getGoal() {
        return goal;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}

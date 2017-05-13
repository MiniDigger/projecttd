package me.minidigger.projecttd.wave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 01/05/2017.
 */
public class Wave {

    private List<WaveGroup> groups;
    private String name;
    private WaveType type = WaveType.NORMAL;
    private int points = 100;
    private float money = 10;

    public void addGroup(WaveGroup group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(WaveType type) {
        this.type = type;
    }

    public WaveType getType() {
        return type;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public List<WaveGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<WaveGroup> groups) {
        this.groups = groups;
    }
}

package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Martin on 03.04.2017.
 */
public class HealthComponent implements Component {

    private double health = 100;

    public HealthComponent(double healthi) {
        health = health;
    }

    public double damage(double damage) {
        health -= damage;
        if (health < 0) {
//            die();
        }
        return health;
    }

    public double heal(double health) {
        return this.health += health;
    }

    public double getHealth() {
        return health;
    }
}

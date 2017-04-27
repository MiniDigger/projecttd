package me.minidigger.projecttd.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by mbenndorf on 27.04.2017.
 */
public class Vector2i {

    public int x;
    public int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

package me.minidigger.projecttd.utils;

import com.badlogic.gdx.math.Vector2;

public class VectorUtil {

    // returns radians
    public static float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    // angle = radians
    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }
}

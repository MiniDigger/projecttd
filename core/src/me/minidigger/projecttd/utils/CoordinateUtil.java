package me.minidigger.projecttd.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Martin on 08.04.2017.
 */
public class CoordinateUtil {

    private static Vector3 vector3 = new Vector3();

    public static Vector2 touchToWorld(Vector2 point, Camera camera) {
        vector3.set(point, 0);
        camera.unproject(vector3);
        point.set(vector3.x, vector3.y);
        return point;
    }

    public static Vector2 alignToGrid(Vector2 input) {
        input.x = (int) (input.x) + 0.5f;
        input.y = (int) (input.y) + 0.5f;
        return input;
    }
}

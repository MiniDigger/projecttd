package me.minidigger.projecttd.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 29/04/2017.
 */
public class LevelManager {

    private List<Level> levels = new ArrayList<>();

    public List<Level> getLevels() {
        if (levels.size() == 0) {
            Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
            Drawable thumb = new TextureRegionDrawable(new TextureRegion(texture));
            levels.add(new Level("Map 1", "map01.tmx", "MiniDigger", thumb));

            Texture texture2 = new Texture(Gdx.files.internal("badlogic.jpg"));
            Drawable thumb2 = new TextureRegionDrawable(new TextureRegion(texture2));
            levels.add(new Level("Map 2", "map02.tmx", "MiniDigger", thumb2));
        }
        return levels;
    }


    private static LevelManager instance = new LevelManager();

    public static LevelManager getInstance() {
        return instance;
    }
}

package me.minidigger.projecttd.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.minidigger.projecttd.entities.Minion;
import me.minidigger.projecttd.wave.Wave;
import me.minidigger.projecttd.wave.WaveBuilder;
import me.minidigger.projecttd.wave.WaveType;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

/**
 * Created by Martin on 29/04/2017.
 */
public class LevelManager {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Level> levels = new ArrayList<>();

    private LevelManager() {
        load();
    }

    public List<Level> getLevels() {
        if (levels.size() == 0) {
            Wave wave = new WaveBuilder().waveType(WaveType.NORMAL).name("Wave 1").points(100).money(10)
                    .group().delay(5).interval(1).health(100).speed(1).type(Minion.MinionType.LAND).count(10).sprite(Minion.SPRITE).finish()
                    .group().delay(20).interval(2).health(300).speed(0.5f).type(Minion.MinionType.LAND).count(5).sprite(Minion.SPRITE).finish()
                    .group().delay(30).interval(1).health(1000).speed(0.3f).type(Minion.MinionType.LAND).count(1).sprite(Minion.SPRITE).finish()
                    .build();
            List<Wave> waves = new ArrayList<>();
            waves.add(wave);

            levels.add(new Level("Map 1", "map01.tmx", "MiniDigger", "badlogic.jpg", waves));

            levels.add(new Level("Map 2", "map02.tmx", "MiniDigger", "badlogic.jpg", waves));

            //DEBUG DEBUG
            try (Writer writer = Gdx.files.absolute("maps/map01.json").writer(false)) {
                gson.toJson(levels.get(0), writer);
            } catch (IOException ex) {
            }

            load();
        }
        return levels;
    }

    public void load() {
        levels = Arrays.stream(Gdx.files.internal("maps").list())
                .filter(f -> f.extension().equalsIgnoreCase("json"))
                .map(f -> gson.fromJson(f.reader(), Level.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Gdx.app.log("DEBUG", "Loaded " + levels.size() + " levels");
    }

    private static LevelManager instance = new LevelManager();

    public static LevelManager getInstance() {
        return instance;
    }
}

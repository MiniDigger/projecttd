package me.minidigger.projecttd.level;

import me.minidigger.projecttd.wave.Wave;

import java.util.List;

/**
 * Created by Martin on 29/04/2017.
 */
public class Level {

    private String name;
    private String file;
    private String author;
    private String thumbnail;
    private List<Wave> waves;

    public Level(String name, String file, String author, String thumbnail, List<Wave> waves) {
        this.name = name;
        this.file = file;
        this.author = author;
        this.thumbnail = thumbnail;
        this.waves = waves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public void setWaves(List<Wave> waves) {
        this.waves = waves;
    }
}

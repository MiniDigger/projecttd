package me.minidigger.projecttd.level;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Martin on 29/04/2017.
 */
public class Level {

    private String name;
    private String file;
    private String author;
    private Drawable thumbnail;
    //TODO info about spawn, troops, etc

    public Level(String name, String file, String author, Drawable thumbnail) {
        this.name = name;
        this.file = file;
        this.author = author;
        this.thumbnail = thumbnail;
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

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }
}

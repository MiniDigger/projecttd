package me.minidigger.projecttd.components;

import com.badlogic.ashley.core.Component;

import me.minidigger.projecttd.pathfinding.FlatTiledSmoothableGraphPath;

/**
 * Created by mbenndorf on 13.04.2017.
 */
public class PathComponent implements Component {

    public FlatTiledSmoothableGraphPath path;
    public int index = 0;
}

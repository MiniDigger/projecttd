package me.minidigger.projecttd.wave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 01/05/2017.
 */
public class Wave {

    private List<WaveGroup> groups = new ArrayList<>();
    
    public void addGroup(WaveGroup group){
        groups.add(group);
    }
}

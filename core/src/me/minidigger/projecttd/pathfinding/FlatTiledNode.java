/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package me.minidigger.projecttd.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**
 * A node for a {@link FlatTiledGraph}.
 *
 * @author davebaol
 */
public class FlatTiledNode {
    /**
     * A constant representing an empty tile
     */
    public static final int TILE_EMPTY = 0;

    /**
     * A constant representing a walkable tile
     */
    public static final int TILE_FLOOR = 1;

    /**
     * A constant representing a wall
     */
    public static final int TILE_WALL = 2;

    /**
     * The x coordinate of this tile
     */
    public final int x;

    /**
     * The y coordinate of this tile
     */
    public final int y;

    /**
     * The type of this tile, see {@link #TILE_EMPTY}, {@link #TILE_FLOOR} and {@link #TILE_WALL}
     */
    public final int type;

    protected Array<Connection<FlatTiledNode>> connections;

    public FlatTiledNode(int x, int y, int type, int connectionCapacity) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.connections = new Array<>(connectionCapacity);
    }

    public Array<Connection<FlatTiledNode>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return x * FlatTiledGraph.sizeY + y;
    }

}
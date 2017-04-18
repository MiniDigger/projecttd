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
     * The x coordinate of this tile
     */
    public final int x;

    /**
     * The y coordinate of this tile
     */
    public final int y;

    /**
     * The type of this tile
     */
    public TileType type;

    public int sizeY;

    protected Array<Connection<FlatTiledNode>> connections;

    public FlatTiledNode(int x, int y, TileType type, int connectionCapacity, int sizeY) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.connections = new Array<>(connectionCapacity);
    }

    public Array<Connection<FlatTiledNode>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return x * sizeY + y;
    }

}
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
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

/**
 * A random generated graph representing a flat tiled map.
 *
 * @author davebaol
 */
public class FlatTiledGraph implements IndexedGraph<FlatTiledNode> {

    protected Array<FlatTiledNode> nodes;

    public boolean diagonal;
    public FlatTiledNode startNode;

    public int sizeX;
    public int sizeY;

    public FlatTiledGraph() {
        this.diagonal = false;
        this.startNode = null;
    }

    public void init(TileType[][] map, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.nodes = new Array<>(sizeX * sizeY);

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                nodes.add(new FlatTiledNode(x, y, map[x][y], 4, sizeY));
            }
        }

        // Each node has up to 4 neighbors, therefore no diagonal movement is possible
        for (int x = 0; x < sizeX; x++) {
            int idx = x * sizeY;
            for (int y = 0; y < sizeY; y++) {
                FlatTiledNode n = nodes.get(idx + y);
                if (x > 0) addConnection(n, -1, 0);
                if (y > 0) addConnection(n, 0, -1);
                if (x < sizeX - 1) addConnection(n, 1, 0);
                if (y < sizeY - 1) addConnection(n, 0, 1);
            }
        }
    }

    public FlatTiledNode getNode(int x, int y) {
        return nodes.get(x * sizeY + y);
    }

    public FlatTiledNode getNode(int index) {
        return nodes.get(index);
    }

    @Override
    public int getIndex(FlatTiledNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<FlatTiledNode>> getConnections(FlatTiledNode fromNode) {
        return fromNode.getConnections();
    }

    private void addConnection(FlatTiledNode n, int xOffset, int yOffset) {
        FlatTiledNode target = getNode(n.x + xOffset, n.y + yOffset);
        if (target.type == TileType.FLOOR)
            n.getConnections().add(new FlatTiledConnection(this, n, target));
    }

    public void updateTile(int x, int y, TileType type) {
        FlatTiledNode node = getNode(x, y);
        node.type = type;
        if (x > 0) updateConnection(node, -1, 0);
        if (y > 0) updateConnection(node, 0, -1);
        if (x < sizeX - 1) updateConnection(node, 1, 0);
        if (y < sizeY - 1) updateConnection(node, 0, 1);
    }

    private void updateConnection(FlatTiledNode n, int xOffset, int yOffset) {
        FlatTiledNode target = getNode(n.x + xOffset, n.y + yOffset);
        boolean wasThere = false;
        for (int i = 0; i < n.getConnections().size; i++) {
            Connection connection = n.getConnections().get(i);
            if (connection.getFromNode().equals(n) && connection.getToNode().equals(target)) {
                wasThere = true;
                // no longer walkable -> remove
                if (target.type != TileType.FLOOR) {
                    n.getConnections().removeIndex(i);
                    break;
                }
            }
        }

        if (!wasThere) {
            // new connection, wooo
            if (target.type == TileType.FLOOR) {
                n.getConnections().add(new FlatTiledConnection(this, n, target));
            }
        }
    }
}
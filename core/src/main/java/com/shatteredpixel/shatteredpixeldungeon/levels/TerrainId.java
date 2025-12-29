package com.shatteredpixel.shatteredpixeldungeon.levels;

import java.util.Objects;

/**
 * Value object for Terrain tile id (int code). Still compatible with
 * Terrain.flags[id].
 */
public final class TerrainId {

    private final int id;

    private TerrainId(int id) {
        this.id = id;
    }

    public static TerrainId of(int id) {
        return new TerrainId(id);
    }

    public int id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TerrainId))
            return false;
        TerrainId terrainId = (TerrainId) o;
        return id == terrainId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TerrainId(" + id + ")";
    }
}

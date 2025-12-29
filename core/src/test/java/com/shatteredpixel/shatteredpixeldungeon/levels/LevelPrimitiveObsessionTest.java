package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.watabou.utils.SparseArray;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LevelPrimitiveObsessionTest {

    private static class TestLevel extends Level {
        @Override
        protected boolean build() {
            return true;
        }

        @Override
        protected void createItems() {
        }

        @Override
        protected void createMobs() {
        }
    }

    @Test
    public void p1_set_overload_updates_map_and_flag_arrays_same_as_flagsTable() {
        TestLevel level = new TestLevel();
        level.setSize(7, 7); // creates arrays, PathFinder size etc. :contentReference[oaicite:8]{index=8}
        level.traps = new SparseArray<>(); // Level.set(...) expects traps not null in some paths

        int cellIndex = 3 * 7 + 3; // center cell, avoid NEIGHBOURS out-of-bound
        TerrainId terrain = TerrainId.of(Terrain.WATER);

        Level.set(LevelCell.of(cellIndex), terrain, level);

        assertEquals(Terrain.WATER, level.map[cellIndex]);

        int flags = Terrain.flags[Terrain.WATER];

        assertEquals((flags & Terrain.PASSABLE) != 0, level.passable[cellIndex]);
        assertEquals((flags & Terrain.LOS_BLOCKING) != 0, level.losBlocking[cellIndex]);
        assertEquals((flags & Terrain.FLAMABLE) != 0, level.flamable[cellIndex]);
        assertEquals((flags & Terrain.SECRET) != 0, level.secret[cellIndex]);
        assertEquals((flags & Terrain.SOLID) != 0, level.solid[cellIndex]);
        assertEquals((flags & Terrain.AVOID) != 0, level.avoid[cellIndex]);
        assertEquals((flags & Terrain.PIT) != 0, level.pit[cellIndex]);

        // Level.set has special rule: water[cell] is true iff terrain == Terrain.WATER
        // :contentReference[oaicite:9]{index=9}
        assertTrue(level.water[cellIndex]);
    }

    @Test
    public void p1_value_objects_are_equal_and_hashable() {
        assertEquals(LevelCell.of(10), LevelCell.of(10));
        assertNotEquals(LevelCell.of(10), LevelCell.of(11));

        assertEquals(TerrainId.of(5), TerrainId.of(5));
        assertNotEquals(TerrainId.of(5), TerrainId.of(6));
    }

    @Test
    public void p2_bundle_keys_are_nonEmpty_and_unique() {
        Set<String> keys = new HashSet<>();

        assertTrue(keys.add(LevelBundleKeys.VERSION));
        assertTrue(keys.add(LevelBundleKeys.WIDTH));
        assertTrue(keys.add(LevelBundleKeys.HEIGHT));
        assertTrue(keys.add(LevelBundleKeys.MAP));
        assertTrue(keys.add(LevelBundleKeys.VISITED));
        assertTrue(keys.add(LevelBundleKeys.MAPPED));
        assertTrue(keys.add(LevelBundleKeys.TRANSITIONS));
        assertTrue(keys.add(LevelBundleKeys.LOCKED));
        assertTrue(keys.add(LevelBundleKeys.HEAPS));
        assertTrue(keys.add(LevelBundleKeys.PLANTS));
        assertTrue(keys.add(LevelBundleKeys.TRAPS));
        assertTrue(keys.add(LevelBundleKeys.CUSTOM_TILES));
        assertTrue(keys.add(LevelBundleKeys.CUSTOM_WALLS));
        assertTrue(keys.add(LevelBundleKeys.MOBS));
        assertTrue(keys.add(LevelBundleKeys.BLOBS));
        assertTrue(keys.add(LevelBundleKeys.FEELING));
        assertTrue(keys.add(LevelBundleKeys.MOBS_TO_SPAWN));
        assertTrue(keys.add(LevelBundleKeys.RESPAWNER));

        for (String k : keys) {
            assertNotNull(k);
            assertFalse(k.trim().isEmpty());
        }
    }
}

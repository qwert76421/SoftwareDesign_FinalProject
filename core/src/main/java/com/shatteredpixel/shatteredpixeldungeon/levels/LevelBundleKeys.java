package com.shatteredpixel.shatteredpixeldungeon.levels;

/**
 * Centralized Bundle keys for Level serialization.
 * Prevents stringly-typed bugs and makes refactor safer.
 */
public final class LevelBundleKeys {

    private LevelBundleKeys() {
    }

    public static final String VERSION = "version";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String MAP = "map";
    public static final String VISITED = "visited";
    public static final String MAPPED = "mapped";
    public static final String TRANSITIONS = "transitions";
    public static final String LOCKED = "locked";
    public static final String HEAPS = "heaps";
    public static final String PLANTS = "plants";
    public static final String TRAPS = "traps";
    public static final String CUSTOM_TILES = "customTiles";
    public static final String CUSTOM_WALLS = "customWalls";
    public static final String MOBS = "mobs";
    public static final String BLOBS = "blobs";
    public static final String FEELING = "feeling";

    // literals currently used directly in Level.java
    public static final String MOBS_TO_SPAWN = "mobs_to_spawn";
    public static final String RESPAWNER = "respawner";
}

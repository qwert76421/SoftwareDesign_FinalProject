package com.shatteredpixel.shatteredpixeldungeon.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;

/**
 * Parameter Object for Painter:
 * groups (Level level, int value) together to reduce data clumps.
 */
public final class PaintTarget {
    public final Level level;
    public final int value;

    public PaintTarget(Level level, int value) {
        if (level == null)
            throw new IllegalArgumentException("level must not be null");
        this.level = level;
        this.value = value;
    }

    public static PaintTarget of(Level level, int value) {
        return new PaintTarget(level, value);
    }
}

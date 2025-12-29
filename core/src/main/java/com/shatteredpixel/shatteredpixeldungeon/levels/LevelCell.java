package com.shatteredpixel.shatteredpixeldungeon.levels;

import java.util.Objects;

/**
 * Value object for a cell index in a Level (prevents mixing with terrain id /
 * flags).
 */
public final class LevelCell {

    private final int index;

    private LevelCell(int index) {
        this.index = index;
    }

    public static LevelCell of(int index) {
        return new LevelCell(index);
    }

    public int index() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LevelCell))
            return false;
        LevelCell levelCell = (LevelCell) o;
        return index == levelCell.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "LevelCell(" + index + ")";
    }
}

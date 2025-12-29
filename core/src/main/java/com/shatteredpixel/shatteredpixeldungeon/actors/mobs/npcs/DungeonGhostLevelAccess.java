package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

/**
 * 真正的資料來源仍然是 Dungeon.level
 * 但「heaps.get / exit()」細節被封裝在這裡，Ghost 本體不需要知道。
 */
final class DungeonGhostLevelAccess implements GhostLevelAccess {

    @Override
    public boolean hasHeapAt(int pos) {
        return Dungeon.level.heaps.get(pos) != null;
    }

    @Override
    public boolean isExitCell(int pos) {
        return pos == Dungeon.level.exit();
    }
}

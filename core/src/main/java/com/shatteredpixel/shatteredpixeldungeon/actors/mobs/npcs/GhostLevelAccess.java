package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

/**
 * 目的：避免 Ghost 直接知道 Dungeon.level.heaps / exit 的內部結構
 * Ghost 只需要問：「某格有沒有 heap？」、「某格是不是出口？」
 */
interface GhostLevelAccess {
    boolean hasHeapAt(int pos);

    boolean isExitCell(int pos);
}

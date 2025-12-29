package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

/**
 * 純邏輯規則：Ghost 隨機走路的目的地過濾
 * - 有 heap 或出口 => 禁止 (回傳 -1)
 * - 否則 => 允許 (回傳原 pos)
 */
public final class GhostWanderingPolicy {

    private GhostWanderingPolicy() {
    }

    public static int filterDestination(int candidatePos, GhostLevelAccess access) {
        if (candidatePos < 0)
            return -1; // 保守處理
        if (access.hasHeapAt(candidatePos))
            return -1;
        if (access.isExitCell(candidatePos))
            return -1;
        return candidatePos;
    }
}

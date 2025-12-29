package com.shatteredpixel.shatteredpixeldungeon.levels;

/**
 * 純邏輯規則：從 RegularLevel.levelExplorePercent(depth) 抽出的探索度規則
 * 目標：
 * - 把「哪些情況視為 miss」與「missedRooms 數量 -> 探索分數」獨立出來
 * - 可用決策表寫單元測試
 */
public final class LevelExplorePercentRules {

    private LevelExplorePercentRules() {
    }

    /** 簡化版 heap type（避免 test 依賴 Heap.Type 本體） */
    public enum HeapType {
        HEAP,
        FOR_SALE,
        CRYSTAL_CHEST,
        OTHER
    }

    /**
     * 對應 levelExplorePercent 裡的 heap 規則：
     * - autoExplored => 忽略（不算 miss）
     * - 沒 seen 或 type 不是 (HEAP/FOR_SALE/CRYSTAL_CHEST) => miss
     * - 否則：若 heap 裡含 Key => miss
     */
    public static boolean heapMarksRoomMissed(
            boolean autoExplored,
            boolean seen,
            HeapType type,
            boolean containsKey) {
        if (autoExplored)
            return false;

        boolean allowed = (type == HeapType.HEAP || type == HeapType.FOR_SALE || type == HeapType.CRYSTAL_CHEST);
        if (!seen || !allowed)
            return true;

        return containsKey;
    }

    /**
     * 對應原本最後的 switch(missedRooms.size())
     */
    public static float scoreFromMissedRoomsCount(int missedRooms) {
        switch (missedRooms) {
            case 0:
                return 1f;
            case 1:
                return 0.5f;
            case 2:
                return 0.2f;
            default:
                return 0f;
        }
    }

    /**
     * 地形是否屬於「未探索房間的線索」：
     * Barricade / Locked Door / Secret Door
     */
    public static boolean isBlockingDoorTerrain(int terrain) {
        return terrain == Terrain.BARRICADE
                || terrain == Terrain.LOCKED_DOOR
                || terrain == Terrain.SECRET_DOOR;
    }
}

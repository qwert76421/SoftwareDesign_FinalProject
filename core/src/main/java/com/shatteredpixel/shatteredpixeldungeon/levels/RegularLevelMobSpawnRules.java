package com.shatteredpixel.shatteredpixeldungeon.levels;

/**
 * 純邏輯規則：從 RegularLevel.createMobs() 抽出的「是否允許生成在某格」判斷。
 * 目標：
 * - 將長 do-while 的複合條件搬出來，讓 createMobs() 更短、更可讀
 * - 規則可被 JUnit 直接測（不依賴 Dungeon / Random / 地圖）
 */
public final class RegularLevelMobSpawnRules {

    private RegularLevelMobSpawnRules() {
    }

    /**
     * 回傳 true 代表「這格不適合，應該重抽位置」（對應原本 while 條件）
     */
    public static boolean shouldRetryPlacement(
            boolean cellOccupied, // findMob(pos) != null
            boolean inEntranceFOV, // entranceFOV[pos]
            boolean distanceIsFinite, // PathFinder.distance[pos] != Integer.MAX_VALUE
            boolean passable, // passable[pos]
            boolean solid, // solid[pos]
            boolean canPlaceInRoom, // room.canPlaceCharacter(...)
            boolean isExitCell, // pos == exit()
            boolean hasTrap, // traps.get(pos) != null
            boolean hasPlant, // plants.get(pos) != null
            boolean isLargeMob, // mob.properties contains LARGE
            boolean openSpace // openSpace[pos]
    ) {
        if (cellOccupied)
            return true;
        if (inEntranceFOV)
            return true;
        if (distanceIsFinite)
            return true;
        if (!passable)
            return true;
        if (solid)
            return true;
        if (!canPlaceInRoom)
            return true;
        if (isExitCell)
            return true;
        if (hasTrap || hasPlant)
            return true;

        // 大型怪需要 open space
        if (isLargeMob && !openSpace)
            return true;

        return false;
    }

    /**
     * createMobs() 的起始生成數規則：depth=1 固定 8，其餘使用 mobLimit()
     */
    public static int mobsToSpawn(int depth, int mobLimit) {
        return depth == 1 ? 8 : mobLimit;
    }
}

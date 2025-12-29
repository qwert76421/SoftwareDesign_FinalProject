package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.items.Heap;

/**
 * 純邏輯規則：從 createItems() 抽出的「物品掉落容器 / mimic / locked chest」決策。
 *
 * 原則：
 * - 不碰 Random / Dungeon / mobs / drop / heaps / map
 * - 只做「輸入 -> 輸出」的決策，方便用 JUnit + 決策表測試
 */
public final class RegularLevelItemSpawnRules {

    private RegularLevelItemSpawnRules() {
    }

    /** createItems() 的 switch(Random.Int(20)) 決策結果 */
    public static final class BaseDropDecision {
        public final boolean spawnNormalMimic;
        public final Heap.Type heapTypeIfNotMimic;

        private BaseDropDecision(boolean spawnNormalMimic, Heap.Type heapTypeIfNotMimic) {
            this.spawnNormalMimic = spawnNormalMimic;
            this.heapTypeIfNotMimic = heapTypeIfNotMimic;
        }

        public static BaseDropDecision spawnMimic() {
            return new BaseDropDecision(true, null);
        }

        public static BaseDropDecision dropAs(Heap.Type type) {
            return new BaseDropDecision(false, type);
        }
    }

    /** special drop (artifact / upgradable) 的結果 */
    public enum SpecialOutcome {
        NONE,
        GOLDEN_MIMIC,
        LOCKED_CHEST_WITH_KEY
    }

    /**
     * Base container/mimic 決策（對應 createItems() 的 switch(Random.Int(20))）
     *
     * @param roll20              Random.Int(20)
     * @param depth               Dungeon.depth
     * @param cellHasMob          findMob(cell) != null
     * @param mimicMultiplier     MimicTooth.mimicChanceMultiplier()
     * @param chestMimicRollFloat 只有 roll20 在 1..4 時才會用到的 Random.Float()；其他情況可傳
     *                            Float.NaN
     */
    public static BaseDropDecision decideBaseDrop(
            int roll20,
            int depth,
            boolean cellHasMob,
            float mimicMultiplier,
            float chestMimicRollFloat) {
        switch (roll20) {
            case 0:
                return BaseDropDecision.dropAs(Heap.Type.SKELETON);

            case 1:
            case 2:
            case 3:
            case 4: {
                float threshold = chestMimicChanceThreshold(mimicMultiplier);
                boolean shouldSpawn = !cellHasMob
                        && !Float.isNaN(chestMimicRollFloat)
                        && chestMimicRollFloat < threshold;

                if (shouldSpawn)
                    return BaseDropDecision.spawnMimic();
                return BaseDropDecision.dropAs(Heap.Type.CHEST);
            }

            case 5:
                if (depth > 1 && !cellHasMob)
                    return BaseDropDecision.spawnMimic();
                return BaseDropDecision.dropAs(Heap.Type.CHEST);

            default:
                return BaseDropDecision.dropAs(Heap.Type.HEAP);
        }
    }

    /**
     * Special drop 決策（對應 createItems() 裡 artifact/upgradable 那段）
     *
     * @param specialTriggered     由 createItems() 依原本 short-circuit Random.Int(...)
     *                             計算出的布林
     * @param depth                Dungeon.depth
     * @param cellHasMob           findMob(cell) != null
     * @param mimicMultiplier      MimicTooth.mimicChanceMultiplier()
     * @param goldenMimicRollFloat 只有 depth>1 時才會用到的 Random.Float()；depth<=1 可傳
     *                             Float.NaN（避免改 RNG 次數）
     */
    public static SpecialOutcome decideSpecialDrop(
            boolean specialTriggered,
            int depth,
            boolean cellHasMob,
            float mimicMultiplier,
            float goldenMimicRollFloat) {
        if (!specialTriggered)
            return SpecialOutcome.NONE;

        float chance = goldenMimicChance(mimicMultiplier);

        boolean shouldGolden = depth > 1
                && !cellHasMob
                && !Float.isNaN(goldenMimicRollFloat)
                && goldenMimicRollFloat < chance;

        return shouldGolden ? SpecialOutcome.GOLDEN_MIMIC : SpecialOutcome.LOCKED_CHEST_WITH_KEY;
    }

    /** case 1..4 的 normal mimic 門檻： (mult-1)/4 */
    public static float chestMimicChanceThreshold(float mimicMultiplier) {
        return (mimicMultiplier - 1f) / 4f;
    }

    /** special（artifact/upgradable） golden mimic 機率： 0.1 * mult */
    public static float goldenMimicChance(float mimicMultiplier) {
        return (1f / 10f) * mimicMultiplier;
    }
}

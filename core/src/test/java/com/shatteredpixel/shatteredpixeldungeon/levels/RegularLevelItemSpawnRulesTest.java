package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegularLevelItemSpawnRulesTest {

    // -------------------------
    // Decision Table #1：Base Container + Normal Mimic
    // -------------------------

    @Test
    void R1_roll0_shouldBeSkeleton() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                0, 2, false, 1f, Float.NaN);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.SKELETON, d.heapTypeIfNotMimic);
    }

    @Test
    void R2_roll1to4_noMob_andFloatBelowThreshold_shouldSpawnNormalMimic() {
        // mimicMultiplier=2 => threshold=(2-1)/4=0.25
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                2, 2, false, 2f, 0.10f);
        assertTrue(d.spawnNormalMimic);
        assertNull(d.heapTypeIfNotMimic);
    }

    @Test
    void R3_roll1to4_noMob_andFloatAboveThreshold_shouldBeChest() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                4, 2, false, 2f, 0.30f);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.CHEST, d.heapTypeIfNotMimic);
    }

    @Test
    void R4_roll1to4_hasMob_shouldBeChest_evenIfFloatBelowThreshold() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                3, 2, true, 2f, 0.10f);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.CHEST, d.heapTypeIfNotMimic);
    }

    @Test
    void R5_roll5_depthGt1_noMob_shouldSpawnNormalMimic() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                5, 2, false, 2f, Float.NaN);
        assertTrue(d.spawnNormalMimic);
    }

    @Test
    void R6_roll5_depthGt1_hasMob_shouldBeChest() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                5, 2, true, 2f, Float.NaN);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.CHEST, d.heapTypeIfNotMimic);
    }

    @Test
    void R7_roll5_depthEq1_shouldBeChest() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                5, 1, false, 2f, Float.NaN);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.CHEST, d.heapTypeIfNotMimic);
    }

    @Test
    void R8_rollOthers_shouldBeHeap() {
        var d = RegularLevelItemSpawnRules.decideBaseDrop(
                19, 2, false, 2f, Float.NaN);
        assertFalse(d.spawnNormalMimic);
        assertEquals(Heap.Type.HEAP, d.heapTypeIfNotMimic);
    }

    // -------------------------
    // Decision Table #2：Special Drop (Golden Mimic / Locked Chest)
    // -------------------------

    @Test
    void S1_notTriggered_shouldBeNone() {
        var out = RegularLevelItemSpawnRules.decideSpecialDrop(
                false, 2, false, 2f, 0.01f);
        assertEquals(RegularLevelItemSpawnRules.SpecialOutcome.NONE, out);
    }

    @Test
    void S2_triggered_depthGt1_noMob_floatBelowChance_shouldBeGoldenMimic() {
        // mimicMultiplier=2 => chance=0.2
        var out = RegularLevelItemSpawnRules.decideSpecialDrop(
                true, 2, false, 2f, 0.05f);
        assertEquals(RegularLevelItemSpawnRules.SpecialOutcome.GOLDEN_MIMIC, out);
    }

    @Test
    void S3_triggered_hasMob_shouldBeLockedChest() {
        var out = RegularLevelItemSpawnRules.decideSpecialDrop(
                true, 2, true, 2f, 0.05f);
        assertEquals(RegularLevelItemSpawnRules.SpecialOutcome.LOCKED_CHEST_WITH_KEY, out);
    }

    @Test
    void S4_triggered_depthEq1_shouldBeLockedChest_withoutConsumingFloat() {
        // depth=1 時，createItems 原碼不會呼叫 Random.Float()；我們用 NaN 表示「沒有抽」
        var out = RegularLevelItemSpawnRules.decideSpecialDrop(
                true, 1, false, 2f, Float.NaN);
        assertEquals(RegularLevelItemSpawnRules.SpecialOutcome.LOCKED_CHEST_WITH_KEY, out);
    }
}

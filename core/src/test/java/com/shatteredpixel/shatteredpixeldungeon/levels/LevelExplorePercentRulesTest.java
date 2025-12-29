package com.shatteredpixel.shatteredpixeldungeon.levels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelExplorePercentRulesTest {

    // -------------------------
    // Decision Table：Heap marks missed room
    // 規則：
    // - autoExplored => ignore (not missed)
    // - !seen OR type not in (HEAP/FOR_SALE/CRYSTAL_CHEST) => missed
    // - else containsKey => missed, otherwise not missed
    // -------------------------

    // H1 autoExplored => NOT missed
    @Test
    void H1_autoExplored_shouldNotMiss() {
        assertFalse(LevelExplorePercentRules.heapMarksRoomMissed(
                true, false, LevelExplorePercentRules.HeapType.OTHER, true));
    }

    // H2 not autoExplored + not seen => missed
    @Test
    void H2_notSeen_shouldMiss() {
        assertTrue(LevelExplorePercentRules.heapMarksRoomMissed(
                false, false, LevelExplorePercentRules.HeapType.HEAP, false));
    }

    // H3 seen + type not allowed => missed
    @Test
    void H3_seen_butTypeNotAllowed_shouldMiss() {
        assertTrue(LevelExplorePercentRules.heapMarksRoomMissed(
                false, true, LevelExplorePercentRules.HeapType.OTHER, false));
    }

    // H4 seen + allowed type + contains key => missed
    @Test
    void H4_seen_allowed_containsKey_shouldMiss() {
        assertTrue(LevelExplorePercentRules.heapMarksRoomMissed(
                false, true, LevelExplorePercentRules.HeapType.HEAP, true));
        assertTrue(LevelExplorePercentRules.heapMarksRoomMissed(
                false, true, LevelExplorePercentRules.HeapType.FOR_SALE, true));
        assertTrue(LevelExplorePercentRules.heapMarksRoomMissed(
                false, true, LevelExplorePercentRules.HeapType.CRYSTAL_CHEST, true));
    }

    // H5 seen + allowed type + no key => NOT missed
    @Test
    void H5_seen_allowed_noKey_shouldNotMiss() {
        assertFalse(LevelExplorePercentRules.heapMarksRoomMissed(
                false, true, LevelExplorePercentRules.HeapType.HEAP, false));
    }

    // -------------------------
    // scoreFromMissedRoomsCount
    // -------------------------

    @Test
    void score_missed0_is1() {
        assertEquals(1f, LevelExplorePercentRules.scoreFromMissedRoomsCount(0));
    }

    @Test
    void score_missed1_is0_5() {
        assertEquals(0.5f, LevelExplorePercentRules.scoreFromMissedRoomsCount(1));
    }

    @Test
    void score_missed2_is0_2() {
        assertEquals(0.2f, LevelExplorePercentRules.scoreFromMissedRoomsCount(2));
    }

    @Test
    void score_missed3OrMore_is0() {
        assertEquals(0f, LevelExplorePercentRules.scoreFromMissedRoomsCount(3));
        assertEquals(0f, LevelExplorePercentRules.scoreFromMissedRoomsCount(99));
    }

    // -------------------------
    // isBlockingDoorTerrain
    // -------------------------

    @Test
    void blockingDoorTerrain_shouldMatchThreeTypes() {
        assertTrue(LevelExplorePercentRules.isBlockingDoorTerrain(Terrain.BARRICADE));
        assertTrue(LevelExplorePercentRules.isBlockingDoorTerrain(Terrain.LOCKED_DOOR));
        assertTrue(LevelExplorePercentRules.isBlockingDoorTerrain(Terrain.SECRET_DOOR));

        assertFalse(LevelExplorePercentRules.isBlockingDoorTerrain(Terrain.DOOR));
        assertFalse(LevelExplorePercentRules.isBlockingDoorTerrain(Terrain.EMPTY));
    }
}

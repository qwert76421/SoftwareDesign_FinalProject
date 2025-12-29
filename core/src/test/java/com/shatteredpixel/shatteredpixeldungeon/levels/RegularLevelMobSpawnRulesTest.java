package com.shatteredpixel.shatteredpixeldungeon.levels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegularLevelMobSpawnRulesTest {

    // 一個「完全合法」的 baseline
    private static boolean retryWithOverrides(
            Boolean cellOccupied,
            Boolean inEntranceFOV,
            Boolean distanceIsFinite,
            Boolean passable,
            Boolean solid,
            Boolean canPlaceInRoom,
            Boolean isExitCell,
            Boolean hasTrap,
            Boolean hasPlant,
            Boolean isLargeMob,
            Boolean openSpace) {
        // baseline：全部都 OK
        boolean base_cellOccupied = false;
        boolean base_inEntranceFOV = false;
        boolean base_distanceFinite = false; // 必須是 false 才合法（== Integer.MAX_VALUE）
        boolean base_passable = true;
        boolean base_solid = false;
        boolean base_canPlaceInRoom = true;
        boolean base_isExitCell = false;
        boolean base_hasTrap = false;
        boolean base_hasPlant = false;
        boolean base_isLargeMob = false;
        boolean base_openSpace = true;

        return RegularLevelMobSpawnRules.shouldRetryPlacement(
                cellOccupied != null ? cellOccupied : base_cellOccupied,
                inEntranceFOV != null ? inEntranceFOV : base_inEntranceFOV,
                distanceIsFinite != null ? distanceIsFinite : base_distanceFinite,
                passable != null ? passable : base_passable,
                solid != null ? solid : base_solid,
                canPlaceInRoom != null ? canPlaceInRoom : base_canPlaceInRoom,
                isExitCell != null ? isExitCell : base_isExitCell,
                hasTrap != null ? hasTrap : base_hasTrap,
                hasPlant != null ? hasPlant : base_hasPlant,
                isLargeMob != null ? isLargeMob : base_isLargeMob,
                openSpace != null ? openSpace : base_openSpace);
    }

    @Test
    void M0_allValid_shouldNotRetry() {
        assertFalse(retryWithOverrides(null, null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    void M1_cellOccupied_shouldRetry() {
        assertTrue(retryWithOverrides(true, null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    void M2_inEntranceFOV_shouldRetry() {
        assertTrue(retryWithOverrides(null, true, null, null, null, null, null, null, null, null, null));
    }

    @Test
    void M3_distanceFinite_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, true, null, null, null, null, null, null, null, null));
    }

    @Test
    void M4_notPassable_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, false, null, null, null, null, null, null, null));
    }

    @Test
    void M5_solid_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, null, true, null, null, null, null, null, null));
    }

    @Test
    void M6_cannotPlaceInRoom_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, null, null, false, null, null, null, null, null));
    }

    @Test
    void M7_exitCell_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, null, null, null, true, null, null, null, null));
    }

    @Test
    void M8_hasTrap_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, null, null, null, null, true, null, null, null));
    }

    @Test
    void M9_hasPlant_shouldRetry() {
        assertTrue(retryWithOverrides(null, null, null, null, null, null, null, null, true, null, null));
    }

    @Test
    void M10_largeMob_requiresOpenSpace_shouldRetryWhenNoOpenSpace() {
        // 大型怪 + 非 openSpace -> retry
        assertTrue(retryWithOverrides(null, null, null, null, null, null, null, null, null, true, false));
    }

    @Test
    void M11_largeMob_withOpenSpace_shouldNotRetry() {
        // 大型怪 + openSpace -> OK
        assertFalse(retryWithOverrides(null, null, null, null, null, null, null, null, null, true, true));
    }

    @Test
    void mobsToSpawn_depth1_is8() {
        assertEquals(8, RegularLevelMobSpawnRules.mobsToSpawn(1, 999));
    }

    @Test
    void mobsToSpawn_otherDepth_useMobLimit() {
        assertEquals(12, RegularLevelMobSpawnRules.mobsToSpawn(7, 12));
    }
}

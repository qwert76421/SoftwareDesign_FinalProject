package com.shatteredpixel.shatteredpixeldungeon.levels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TerrainTest {

    @Test
    void discover_secretDoor_shouldBecomeDoor() {
        assertEquals(Terrain.DOOR, Terrain.discover(Terrain.SECRET_DOOR));

        // 改錯，確認測試真的會抓錯：
        // assertEquals(Terrain.WALL, Terrain.discover(Terrain.SECRET_DOOR));
    }

    @Test
    void discover_secretTrap_shouldBecomeTrap() {
        assertEquals(Terrain.TRAP, Terrain.discover(Terrain.SECRET_TRAP));
    }

    @Test
    void discover_nonSecret_shouldRemainSame() {
        // 任何非 SECRET 的地形都應該原樣回傳（原本 switch default 行為）
        assertEquals(Terrain.WALL, Terrain.discover(Terrain.WALL));
    }

    @Test
    void discover_outOfRange_returnsOriginal() {
        // 查表：超出範圍會不會 array index out of bounds
        // 測試用來保證：超出範圍時仍維持原本 switch 的「回傳原值」行為
        assertEquals(999, Terrain.discover(999));
        assertEquals(-5, Terrain.discover(-5));
    }

    @Test
    void flags_length_is256() {
        // 保護「256 → TERRAIN_COUNT」重構：確保 flags 仍正確
        assertEquals(256, Terrain.flags.length);
    }

    @Test
    void flags_secretDoor_isWallPlusSecret() {
        // 保護 static 初始化規則：SECRET_DOOR 必須含 SECRET bit
        assertEquals(Terrain.SECRET, Terrain.flags[Terrain.SECRET_DOOR] & Terrain.SECRET);
        // 並且必須繼承 WALL 的屬性（flags[SECRET_DOOR] = flags[WALL] | SECRET）
        assertEquals(Terrain.flags[Terrain.WALL], Terrain.flags[Terrain.SECRET_DOOR] & Terrain.flags[Terrain.WALL]);
    }

    @Test
    void flags_secretTrap_isEmptyPlusSecret() {
        assertEquals(Terrain.SECRET, Terrain.flags[Terrain.SECRET_TRAP] & Terrain.SECRET);
        assertEquals(Terrain.flags[Terrain.EMPTY], Terrain.flags[Terrain.SECRET_TRAP] & Terrain.flags[Terrain.EMPTY]);
    }
}

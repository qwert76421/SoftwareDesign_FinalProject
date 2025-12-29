package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BeeSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GhostSprite;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MobSpriteRegistryTest {

    /*
     * 決策表（Decision Table）
     *
     * 規則 C1: mobClass為null? C2: 有mapping? 動作
     * R1 否 是 A1 回傳spriteClass
     * R2 否 否 A3 丟IllegalStateException (fail fast)
     * R3 是 - A2 丟IllegalArgumentException
     */

    // R1：有 mapping -> 回傳 spriteClass
    @Test
    void spriteClassFor_whenMappingExists_returnsSpriteClass() {
        assertEquals(BatSprite.class, MobSpriteRegistry.spriteClassFor(Bat.class));
        assertEquals(BeeSprite.class, MobSpriteRegistry.spriteClassFor(Bee.class));
        assertEquals(GhostSprite.class, MobSpriteRegistry.spriteClassFor(Ghost.class));
    }

    // R3：mobClass 為 null -> IllegalArgumentException
    @Test
    void spriteClassFor_whenMobClassNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> MobSpriteRegistry.spriteClassFor(null));
    }

    // R2：沒有 mapping -> IllegalStateException（Fail fast）
    @Test
    void spriteClassFor_whenMappingMissing_throwsIllegalStateException() {
        // Mob.class 本身通常不會被註冊（而且不需要實例化 mob，就能測這個 case）
        assertThrows(IllegalStateException.class, () -> MobSpriteRegistry.spriteClassFor(Mob.class));
    }
}

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeroProgressionRulesTest {

    @Test
    void applyExp_zeroExp_noChange() {
        var r = HeroProgressionRules.applyExp(1, 0, 0);
        assertEquals(1, r.lvl);
        assertEquals(0, r.exp);
        assertEquals(0, r.gainedLevels);
    }

    @Test
    void applyExp_smallExp_noLevelUp() {
        var r = HeroProgressionRules.applyExp(1, 0, 1);
        assertEquals(1, r.lvl);
        assertEquals(1, r.exp);
        assertEquals(0, r.gainedLevels);
    }

    @Test
    void applyExp_exactThreshold_levelUpOnce() {
        int need = HeroProgressionRules.maxExpForLevel(1);
        var r = HeroProgressionRules.applyExp(1, 0, need);
        assertEquals(2, r.lvl);
        assertEquals(0, r.exp);
        assertEquals(1, r.gainedLevels);
    }

    @Test
    void applyExp_overThreshold_hasRemainder() {
        int need = HeroProgressionRules.maxExpForLevel(1);
        var r = HeroProgressionRules.applyExp(1, 0, need + 3);
        assertEquals(2, r.lvl);
        assertEquals(3, r.exp);
        assertEquals(1, r.gainedLevels);
    }

    @Test
    void applyExp_hugeExp_canLevelUpMultipleTimes() {
        var r = HeroProgressionRules.applyExp(1, 0, 999999);
        assertTrue(r.lvl > 2);
        assertTrue(r.gainedLevels >= 2);
        assertTrue(r.exp >= 0);
    }

}

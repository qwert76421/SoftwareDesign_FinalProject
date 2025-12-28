package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroVitalsRulesTest {

    @Test
    void baseHT_formulaShouldMatchDesign() {
        // lvl=1, htBoost=0 -> 20
        assertEquals(20, HeroVitalsRules.baseHT(1, 0));
        // lvl=2 -> 25
        assertEquals(25, HeroVitalsRules.baseHT(2, 0));
        // lvl=10, htBoost=3 -> 20 + 5*9 + 3 = 68
        assertEquals(68, HeroVitalsRules.baseHT(10, 3));
    }

    @Test
    void applyMultiplier_shouldRoundLikeMathRound() {
        // 25 * 1.2 = 30.0 -> 30
        assertEquals(30, HeroVitalsRules.applyMultiplier(25, 1.2f));
        // 21 * 1.1 = 23.1 -> round=23
        assertEquals(23, HeroVitalsRules.applyMultiplier(21, 1.1f));
    }

    @Test
    void newHP_whenBoostHPAndHTIncreases_shouldHealByDelta() {
        // oldHT=20 -> newHT=30, oldHP=10, boostHP=true
        // HP becomes 10 + (30-20)=20, then min(20,30)=20
        assertEquals(20, HeroVitalsRules.newHP(10, 20, 30, true));
    }

    @Test
    void newHP_whenBoostHPFalse_shouldOnlyCapToNewHT() {
        // 不補血，只做上限裁切
        assertEquals(10, HeroVitalsRules.newHP(10, 20, 30, false));
    }

    @Test
    void newHP_whenHTDecreases_shouldCapDown() {
        // newHT 下降時，不管 boostHP，最後都會 cap 到 newHT
        assertEquals(15, HeroVitalsRules.newHP(18, 20, 15, true));
        assertEquals(15, HeroVitalsRules.newHP(18, 20, 15, false));
    }

    @Test
    void applyElixirBoost_shouldAddFlatValue() {
        assertEquals(35, HeroVitalsRules.applyElixirBoost(30, 5));
    }
}

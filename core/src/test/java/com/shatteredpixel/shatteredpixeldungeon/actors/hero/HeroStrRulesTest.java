package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroStrRulesTest {

    @Test
    void totalSTR_shouldSumAllContributions() {
        assertEquals(10, HeroStrRules.totalSTR(10, 0, 0, 0));
        assertEquals(14, HeroStrRules.totalSTR(10, 2, 1, 1));
        assertEquals(18, HeroStrRules.totalSTR(12, 3, 0, 3));
    }

    @Test
    void strongmanBonus_shouldMatchOriginalFloorFormula() {
        // baseSTR=10, points=1 => floor(10*(0.03+0.05))=floor(0.8)=0
        assertEquals(0, HeroStrRules.strongmanBonus(10, 1));

        // baseSTR=20, points=2 => floor(20*(0.03+0.10))=floor(2.6)=2
        assertEquals(2, HeroStrRules.strongmanBonus(20, 2));

        // baseSTR=30, points=3 => floor(30*(0.03+0.15))=floor(5.4)=5
        assertEquals(5, HeroStrRules.strongmanBonus(30, 3));
    }
}

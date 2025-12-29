package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroClassSwitchDefaultTest {

    @Test
    void masteryBadge_shouldNeverBeNull_forAllHeroClasses() {
        for (HeroClass hc : HeroClass.values()) {
            assertNotNull(hc.masteryBadge(), "masteryBadge() should not return null for " + hc);
        }
    }
}

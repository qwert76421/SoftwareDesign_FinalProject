package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

/**
 * 純邏輯規則：只做數學計算，不碰 Hero、不碰 Buff、不碰任何遊戲引擎。
 * 目的：把 Hero.updateHT() 的計算抽離，提升可測試性、降低 Hero 的 Large Class 壓力。
 */
public final class HeroVitalsRules {

    private HeroVitalsRules() {
    }

    /** 對應 Hero.updateHT 內的基礎公式：HT = 20 + 5*(lvl-1) + HTBoost */
    public static int baseHT(int lvl, int htBoost) {
        return 20 + 5 * (lvl - 1) + htBoost;
    }

    /** 對應 Hero.updateHT：HT = round(multiplier * HT) */
    public static int applyMultiplier(int baseHT, float multiplier) {
        return Math.round(multiplier * baseHT);
    }

    /** 對應 Hero.updateHT：若有 ElixirOfMight.HTBoost 則額外加上 boost 值 */
    public static int applyElixirBoost(int ht, int elixirBoost) {
        return ht + elixirBoost;
    }

    /**
     * 對應 Hero.updateHT 的 HP 調整邏輯：
     * - boostHP=true：HP += max(newHT - oldHT, 0)
     * - 最後 HP = min(HP, newHT)
     */
    public static int newHP(int oldHP, int oldHT, int newHT, boolean boostHP) {
        int hp = oldHP;
        if (boostHP) {
            hp += Math.max(newHT - oldHT, 0);
        }
        return Math.min(hp, newHT);
    }
}

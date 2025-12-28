package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

/**
 * 純邏輯規則：STR 計算（純函式）。
 * Hero 只負責收集各來源加成（戒指、buff、天賦…），再交給此類別做加總。
 */
public final class HeroStrRules {

    private HeroStrRules() {
    }

    public static int totalSTR(int baseSTR, int ringBonus, int adrenalineBonus, int strongmanBonus) {
        return baseSTR + ringBonus + adrenalineBonus + strongmanBonus;
    }

    // 把原本 Hero.STR() 裡的 strongman 公式抽出來（行為不變）
    public static int strongmanBonus(int baseSTR, int strongmanPoints) {
        float ratio = 0.03f + 0.05f * strongmanPoints;
        return (int) Math.floor(baseSTR * ratio);
    }
}

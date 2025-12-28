package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

public final class HeroProgressionRules {

    private HeroProgressionRules() {
    }

    public static int maxExpForLevel(int level) {
        return 5 + level * 5; // Hero.maxExp(int lvl)
    }

    public static Result applyExp(int lvl, int exp, int gainedExp) {
        if (gainedExp <= 0)
            return new Result(lvl, exp, 0);

        int newLvl = lvl;
        int newExp = exp + gainedExp;
        int gainedLevels = 0;

        while (newExp >= maxExpForLevel(newLvl)) {
            newExp -= maxExpForLevel(newLvl);
            newLvl++;
            gainedLevels++;
        }
        return new Result(newLvl, newExp, gainedLevels);
    }

    public static final class Result {
        public final int lvl;
        public final int exp;
        public final int gainedLevels;

        public Result(int lvl, int exp, int gainedLevels) {
            this.lvl = lvl;
            this.exp = exp;
            this.gainedLevels = gainedLevels;
        }
    }
}

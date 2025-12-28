package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells.HallowedGround;

public final class HeroExpGainEffects {

    private HeroExpGainEffects() {
    }

    public static void apply(Hero hero, float percent, int gainedExp, Class source) {

        EtherealChains.chainsRecharge chains = hero.buff(EtherealChains.chainsRecharge.class);
        HornOfPlenty.hornRecharge horn = hero.buff(HornOfPlenty.hornRecharge.class);
        AlchemistsToolkit.kitEnergy kit = hero.buff(AlchemistsToolkit.kitEnergy.class);
        MasterThievesArmband.Thievery armband = hero.buff(MasterThievesArmband.Thievery.class);
        Berserk berserk = hero.buff(Berserk.class);

        Talent.RejuvenatingStepsFurrow rj = hero.buff(Talent.RejuvenatingStepsFurrow.class);
        ElementalStrike.ElementalStrikeFurrowCounter es = hero.buff(ElementalStrike.ElementalStrikeFurrowCounter.class);
        HallowedGround.HallowedFurrowTracker hg = hero.buff(HallowedGround.HallowedFurrowTracker.class);

        applyPercent(hero, percent, source,
                chains, horn, kit, armband, berserk,
                hero.belongings,
                rj, es, hg);
    }

    /** 純副作用邏輯，用 Mockito 測 */
    static void applyPercent(
            Hero hero,
            float percent,
            Class source,
            EtherealChains.chainsRecharge chains,
            HornOfPlenty.hornRecharge horn,
            AlchemistsToolkit.kitEnergy kit,
            MasterThievesArmband.Thievery armband,
            Berserk berserk,
            Iterable<Item> belongings,
            Talent.RejuvenatingStepsFurrow rejuvenating,
            ElementalStrike.ElementalStrikeFurrowCounter elementalStrike,
            HallowedGround.HallowedFurrowTracker hallowed) {
        if (chains != null)
            chains.gainExp(percent);
        if (horn != null)
            horn.gainCharge(percent);
        if (kit != null)
            kit.gainCharge(percent);
        if (armband != null)
            armband.gainCharge(percent);
        if (berserk != null)
            berserk.recover(percent);

        if (source != PotionOfExperience.class) {
            if (belongings != null) {
                for (Item i : belongings) {
                    i.onHeroGainExp(percent, hero);
                }
            }
            countDownAndDetachIfNeeded(rejuvenating, percent * 200f);
            countDownAndDetachIfNeeded(elementalStrike, percent * 20f);
            countDownAndDetachIfNeeded(hallowed, percent * 100f);
        }
    }

    private static void countDownAndDetachIfNeeded(Buff buff, float amount) {
        if (buff == null)
            return;

        // 因為這些 buff 都有 countDown() 與 count()，所以用 instanceof 分流
        if (buff instanceof Talent.RejuvenatingStepsFurrow) {
            Talent.RejuvenatingStepsFurrow b = (Talent.RejuvenatingStepsFurrow) buff;
            b.countDown(amount);
            if (b.count() <= 0)
                b.detach();

        } else if (buff instanceof ElementalStrike.ElementalStrikeFurrowCounter) {
            ElementalStrike.ElementalStrikeFurrowCounter b = (ElementalStrike.ElementalStrikeFurrowCounter) buff;
            b.countDown(amount);
            if (b.count() <= 0)
                b.detach();

        } else if (buff instanceof HallowedGround.HallowedFurrowTracker) {
            HallowedGround.HallowedFurrowTracker b = (HallowedGround.HallowedFurrowTracker) buff;
            b.countDown(amount);
            if (b.count() <= 0)
                b.detach();
        }
    }
}

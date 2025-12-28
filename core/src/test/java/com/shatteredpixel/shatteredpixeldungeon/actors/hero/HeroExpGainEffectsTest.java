package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class HeroExpGainEffectsTest {

    @Test
    void applyPercent_allNull_shouldNotThrow() {
        Hero hero = mock(Hero.class);
        assertDoesNotThrow(() -> HeroExpGainEffects.applyPercent(
                hero, 0.5f, Object.class,
                null, null, null, null, null,
                List.of(),
                null, null, null));
    }

    @Test
    void applyPercent_chainsHornBerserk_shouldCallRechargeMethods() {
        Hero hero = mock(Hero.class);

        EtherealChains.chainsRecharge chains = mock(EtherealChains.chainsRecharge.class);
        HornOfPlenty.hornRecharge horn = mock(HornOfPlenty.hornRecharge.class);
        Berserk berserk = mock(Berserk.class);

        HeroExpGainEffects.applyPercent(
                hero, 0.25f, Object.class,
                chains, horn, null, null, berserk,
                List.of(),
                null, null, null);

        verify(chains).gainExp(0.25f);
        verify(horn).gainCharge(0.25f);
        verify(berserk).recover(0.25f);
    }

    @Test
    void applyPercent_sourceIsPotion_shouldNotNotifyItemsOrFurrows() {
        Hero hero = mock(Hero.class);

        Item item = mock(Item.class);
        Talent.RejuvenatingStepsFurrow rj = mock(Talent.RejuvenatingStepsFurrow.class);

        HeroExpGainEffects.applyPercent(
                hero, 0.4f, PotionOfExperience.class,
                null, null, null, null, null,
                List.of(item),
                rj, null, null);

        // source 是 PotionOfExperience → 不會進入 items / furrow 的區塊
        verify(item, never()).onHeroGainExp(anyFloat(), any());
        verify(rj, never()).countDown(anyFloat());
        verify(rj, never()).detach();
    }

    @Test
    void applyPercent_nonPotion_shouldNotifyItems() {
        Hero hero = mock(Hero.class);

        Item item = mock(Item.class);

        HeroExpGainEffects.applyPercent(
                hero, 0.1f, Object.class,
                null, null, null, null, null,
                List.of(item),
                null, null, null);

        verify(item).onHeroGainExp(0.1f, hero);
    }
}

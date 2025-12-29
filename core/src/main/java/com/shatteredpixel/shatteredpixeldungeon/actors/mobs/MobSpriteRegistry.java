package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BeeSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GhostSprite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central registry that maps Mob classes to their Sprite classes.
 *
 * Goal:
 * - Reduce "Parallel Inheritance Hierarchies" maintenance cost
 * - Fail fast when a mapping is missing (clear exception message)
 */
public final class MobSpriteRegistry {

    private static final Map<Class<? extends Mob>, Class<? extends CharSprite>> REGISTRY = new ConcurrentHashMap<>();

    static {
        // Register a few representative mappings (you can extend this list gradually)
        register(Bat.class, BatSprite.class);
        register(Bee.class, BeeSprite.class);
        register(Ghost.class, GhostSprite.class);
    }

    private MobSpriteRegistry() {
    }

    public static void register(Class<? extends Mob> mobClass,
            Class<? extends CharSprite> spriteClass) {

        if (mobClass == null)
            throw new IllegalArgumentException("mobClass must not be null");
        if (spriteClass == null)
            throw new IllegalArgumentException("spriteClass must not be null");

        Class<? extends CharSprite> existing = REGISTRY.putIfAbsent(mobClass, spriteClass);

        // Fail fast if someone tries to re-register with a different sprite
        if (existing != null && existing != spriteClass) {
            throw new IllegalStateException(
                    "Duplicate sprite mapping for mob " + mobClass.getName()
                            + ": existing=" + existing.getName()
                            + ", new=" + spriteClass.getName());
        }
    }

    /**
     * Returns sprite class for a mob class.
     * Fail-fast: throws if mapping is missing.
     */
    public static Class<? extends CharSprite> spriteClassFor(Class<? extends Mob> mobClass) {
        if (mobClass == null)
            throw new IllegalArgumentException("mobClass must not be null");

        Class<? extends CharSprite> spriteClass = REGISTRY.get(mobClass);
        if (spriteClass == null) {
            throw new IllegalStateException(
                    "No sprite mapping registered for mob class: " + mobClass.getName()
                            + ". Fix by either (1) setting mob.spriteClass, or (2) registering it in MobSpriteRegistry.");
        }
        return spriteClass;
    }
}

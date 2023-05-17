package com.sonamorningstar.eternalartifacts.item.tiers;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum WitheringTier {
    WITHERING("withering", ()-> Ingredient.of(Items.WITHER_SKELETON_SKULL));

    private final Tier toolTier;

    WitheringTier(String withering, Supplier<Ingredient> repairIngredient) {
        this.toolTier = new Tier() {
            @Override
            public int getUses() {
                return Tiers.DIAMOND.getUses() * 2;
            }

            @Override
            public float getSpeed() {
                return Tiers.DIAMOND.getSpeed() * 1.5F;
            }

            @Override
            public float getAttackDamageBonus() {
                return Tiers.DIAMOND.getAttackDamageBonus() * 1.75F;
            }

            @Override
            public int getLevel() {
                return Tiers.DIAMOND.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return Tiers.GOLD.getEnchantmentValue();
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(Items.WITHER_SKELETON_SKULL);
            }

            @Override
            public String toString() {
                return EternalArtifacts.MOD_ID + ":" + withering;
            }
        };
    }

    public final Tier getToolTier() {
        return toolTier;
    }
}

package com.sonamorningstar.eternalartifacts.registry;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModTiers {
    WITHERING("withering", 2072, 12F, 3.0F, 3, 23, Ingredient.of(Items.WITHER_SKELETON_SKULL)),
    REGROWTH("regrowth", 864, 9F, 2.0F, 2, 15, Ingredient.of(ModItems.PLANT_MATTER.get()));

    private final Tier toolTier;

    ModTiers(String name, int uses, float speed, float damageBonus, int level, int enchValue, Ingredient repairIngredient) {
        this.toolTier = new Tier() {
            @Override
            public int getUses() {
                return uses;
            }

            @Override
            public float getSpeed() {
                return speed;
            }

            @Override
            public float getAttackDamageBonus() {
                return damageBonus;
            }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public int getEnchantmentValue() {
                return enchValue;
            }

            @Override
            public Ingredient getRepairIngredient() {return repairIngredient;}

            @Override
            public String toString() {
                return EternalArtifacts.MOD_ID + ":" + name;
            }
        };
    }

    public final Tier getToolTier() {
        return toolTier;
    }
}

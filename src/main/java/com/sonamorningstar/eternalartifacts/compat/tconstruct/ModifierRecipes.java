package com.sonamorningstar.eternalartifacts.compat.tconstruct;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.function.Consumer;

public class ModifierRecipes extends RecipeProvider implements IMaterialRecipeHelper  {
    public ModifierRecipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getModId() {
        return EternalArtifacts.MOD_ID;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        String salvageFolder = "tools/modifiers/salvage/";
        String modifierFolder = "tools/modifiers/";

        ModifierRecipeBuilder.modifier(ModModifiers.REGROWTH)
                .addInput(ModItems.PLANT_MATTER.get())
                .setTools(TinkerTags.Items.MODIFIABLE)
                .allowCrystal()
                .setMaxLevel(1)
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(pFinishedRecipeConsumer, prefix(ModModifiers.REGROWTH.getId(), salvageFolder))
                .save(pFinishedRecipeConsumer, prefix(ModModifiers.REGROWTH.getId(), modifierFolder));
    }
}

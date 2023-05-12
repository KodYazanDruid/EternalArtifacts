package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProv extends ItemModelProvider {


    public ItemModelsProv(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, EternalArtifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.ORANGE.get());
        basicItem(ModItems.PLANT_MATTER.get());

        basicItem(ModItems.HOLY_DAGGER.get());
        basicItem(ModItems.MEDKIT.get());

        withExistingParent(ModBlocks.TINKERING_TABLE.getId().getPath(), modLoc("block/tinkering_table"));
    }
}

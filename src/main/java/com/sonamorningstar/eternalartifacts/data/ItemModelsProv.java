package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProv extends ItemModelProvider {

    private ExistingFileHelper existingFileHelper;

    public ItemModelsProv(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, EternalArtifacts.MOD_ID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.ORANGE.get());
        basicItem(ModItems.PLANT_MATTER.get());
        basicItem(ModItems.LENS.get());

        withExistingParent(ModItems.DEMON_EYE_SPAWN_EGG.getId().getPath(), "item/template_spawn_egg");

        basicItem(ModItems.HOLY_DAGGER.get());
        basicItem(ModItems.FROG_LEGS.get());
        basicItem(ModItems.MEDKIT.get());

        withExistingParent(ModBlocks.TINKERING_TABLE.getId().getPath(), modLoc("block/tinkering_table"));

        handheld(ModItems.AXE_OF_REGROWTH.get());
    }

    private void handheld(Item item) {
        String path = item.getRegistryName().getPath();
        withExistingParent(path, "item/handheld").texture("layer0", modLoc("item/"+ path));
    }
}

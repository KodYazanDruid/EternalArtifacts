package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesProv extends BlockStateProvider {
    public BlockStatesProv(DataGenerator generator, ExistingFileHelper existingFileHelperFileHelper) {
        super(generator, EternalArtifacts.MOD_ID, existingFileHelperFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerTinkeringTable();
    }

    private void registerTinkeringTable() {
        horizontalBlock(ModBlocks.TINKERING_TABLE.get(), models().getExistingFile(ModBlocks.TINKERING_TABLE.getId()));
    }

    private static String getName(ItemLike itemProvider) {
        return itemProvider.asItem().getRegistryName().getPath();
    }
}

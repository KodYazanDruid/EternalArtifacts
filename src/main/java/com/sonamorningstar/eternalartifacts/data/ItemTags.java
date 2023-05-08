package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.stream.Collectors;

public class ItemTags extends ItemTagsProvider {

    public static final TagKey<Item> ARTIFACTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(EternalArtifacts.MOD_ID, "artifacts"));
    public static final TagKey<Item> CURIOS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, "curio"));

    public ItemTags(DataGenerator generator, BlockTagsProvider blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, EternalArtifacts.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        tag(ARTIFACTS).add(ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(EternalArtifacts.MOD_ID))
                .collect(Collectors.toList()).toArray(new Item []{})
        );

        tag(CURIOS).add(
                ModItems.HOLY_DAGGER.get()
        );
    }
}

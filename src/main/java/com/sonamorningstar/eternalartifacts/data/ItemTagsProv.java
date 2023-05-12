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
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class ItemTagsProv extends ItemTagsProvider {

    public static final TagKey<Item> ARTIFACTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(EternalArtifacts.MOD_ID, "artifacts"));
    public static final TagKey<Item> FRUITS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "fruits"));

    //Curio types
    public static final TagKey<Item> CURIO = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, "curio"));
    public static final TagKey<Item> CHARM = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, "charm"));


    public ItemTagsProv(DataGenerator generator, BlockTagsProvider blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, EternalArtifacts.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        tag(ARTIFACTS).add(
                ModItems.HOLY_DAGGER.get(),
                ModItems.MEDKIT.get()
        );

        tag(FRUITS).add(
                ModItems.ORANGE.get()
        );

        tag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "fruits/orange"))).add(
                ModItems.ORANGE.get()
        );
        /*
        tag(CURIO).add(

        );*/

        tag(CHARM).add(
                ModItems.HOLY_DAGGER.get(),
                ModItems.MEDKIT.get()
        );
    }
}

package com.sonamorningstar.eternalartifacts.registry;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.item.curio.CurioItem;
import com.sonamorningstar.eternalartifacts.item.curio.artifacts.MedkitItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EternalArtifacts.MOD_ID);

    public static CreativeModeTab CREATIVE_TAB = new CreativeModeTab(EternalArtifacts.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() { return new ItemStack(HOLY_DAGGER.get()); }
    };

    public static final RegistryObject<Item> PLANT_MATTER = ITEMS.register("plant_matter",
            ()-> new Item(new Item.Properties().tab(CREATIVE_TAB)));
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            ()-> new Item(new Item.Properties().tab(CREATIVE_TAB).food(ModFoods.ORANGE)));

    public static final RegistryObject<CurioItem> HOLY_DAGGER = ITEMS.register("holy_dagger", CurioItem::new);
    public static final RegistryObject<CurioItem> MEDKIT = ITEMS.register("medkit", MedkitItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

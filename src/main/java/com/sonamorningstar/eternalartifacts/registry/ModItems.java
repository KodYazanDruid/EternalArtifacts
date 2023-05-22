package com.sonamorningstar.eternalartifacts.registry;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.item.tools.AxeOfRegrowthItem;
import com.sonamorningstar.eternalartifacts.item.tools.WitheringSwordItem;
import com.sonamorningstar.eternalartifacts.item.curios.CurioItem;
import com.sonamorningstar.eternalartifacts.item.curios.artifacts.MedkitItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sonamorningstar.eternalartifacts.EternalArtifacts.CREATIVE_TAB;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EternalArtifacts.MOD_ID);



    public static final RegistryObject<Item> PLANT_MATTER = ITEMS.register("plant_matter",
            ()-> new Item(new Item.Properties().tab(CREATIVE_TAB)));
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            ()-> new Item(new Item.Properties().tab(CREATIVE_TAB).food(ModFoods.ORANGE)));
    public static final RegistryObject<Item> LENS = ITEMS.register("lens",
            ()-> new Item(new Item.Properties().tab(CREATIVE_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> DEMON_EYE_SPAWN_EGG = ITEMS.register("demon_eye_spawn_egg",
            ()-> new ForgeSpawnEggItem(ModEntities.DEMON_EYE, 0xDDA4A4, 0x721212,
                    new Item.Properties().tab(CREATIVE_TAB)));

    public static final RegistryObject<CurioItem> HOLY_DAGGER = ITEMS.register("holy_dagger", CurioItem::new);
    public static final RegistryObject<CurioItem> FROG_LEGS = ITEMS.register("frog_legs", CurioItem::new);
    public static final RegistryObject<CurioItem> MEDKIT = ITEMS.register("medkit", MedkitItem::new);

    public static final RegistryObject<SwordItem> WITHERING_SWORD = ITEMS.register("withering_sword", WitheringSwordItem::new);
    public static final RegistryObject<AxeItem> AXE_OF_REGROWTH = ITEMS.register("axe_of_regrowth", AxeOfRegrowthItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

package com.sonamorningstar.eternalartifacts.registry;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.entity.DemonEyeEntity;
import com.sonamorningstar.eternalartifacts.entity.projectile.CustomWitherSkull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, EternalArtifacts.MOD_ID);

    public static final RegistryObject<EntityType<CustomWitherSkull>> CUSTOM_WITHER_SKULL = ENTITIES.register("custom_wither_skull",
            ()-> EntityType.Builder.<CustomWitherSkull>of(CustomWitherSkull::new, MobCategory.MISC).sized(1.0F, 1.0F).fireImmune().build("custom_wither_skull"));

    public static final RegistryObject<EntityType<DemonEyeEntity>> DEMON_EYE = ENTITIES.register("demon_eye",
            ()-> EntityType.Builder.of(DemonEyeEntity::new, MobCategory.MONSTER)
                    .sized(0.6F , 0.6F)
                    .build(new ResourceLocation(EternalArtifacts.MOD_ID, "demon_eye").toString()));


    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

}

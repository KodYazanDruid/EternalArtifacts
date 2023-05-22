package com.sonamorningstar.eternalartifacts.events;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.entity.DemonEyeEntity;
import com.sonamorningstar.eternalartifacts.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModBusEvents {

    @SubscribeEvent
    public static void entityAttribute(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DEMON_EYE.get(), DemonEyeEntity.setAttributes());
    }
}

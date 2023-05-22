package com.sonamorningstar.eternalartifacts.client.events;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.client.renderers.DemonEyeRenderer;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModEntities;
import com.sonamorningstar.eternalartifacts.registry.ModSlotType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TINKERING_TABLE.get(), RenderType.cutout());

        EntityRenderers.register(ModEntities.DEMON_EYE.get(), DemonEyeRenderer::new);
    }

    @SubscribeEvent
    public static void textureStitchEventPre(final TextureStitchEvent.Pre event) {
        if(!Objects.equals(event.getAtlas().location(), InventoryMenu.BLOCK_ATLAS)) { return; }
        for(ModSlotType type : ModSlotType.values()) { event.addSprite(type.getIcon()); }
    }
}

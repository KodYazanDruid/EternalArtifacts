package com.sonamorningstar.eternalartifacts.events.curios;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.events.CurioEvents;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID)
public class MedkitEvents extends CurioEvents {

    @SubscribeEvent
    public static void onPlayerCombat(LivingAttackEvent event) {
        Item medkit = ModItems.MEDKIT.get();
        if (!event.getEntity().level.isClientSide()
                && event.getSource().getEntity() instanceof Player player
                && hasCurio(medkit, player)) {
            resetCooldown(medkit, player, 120);
        }
        if (!event.getEntity().level.isClientSide()
                && event.getEntityLiving() instanceof Player player
                && hasCurio(medkit, player)) {
            resetCooldown(medkit, player, 120);
        }
    }
}

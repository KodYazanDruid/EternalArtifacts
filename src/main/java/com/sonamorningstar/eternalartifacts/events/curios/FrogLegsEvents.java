package com.sonamorningstar.eternalartifacts.events.curios;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.events.CurioEvents;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID)
public class FrogLegsEvents extends CurioEvents {

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        Item frogLegs = ModItems.FROG_LEGS.get();
        LivingEntity living = event.getEntityLiving();
        if(!living.level.isClientSide()
                && living instanceof Player player
                && hasCurio(frogLegs, player)) {
            if(!player.isCrouching()) {
                player.hurtMarked = true;
                player.setDeltaMovement(player.getDeltaMovement().add(0.0D, 0.2F,0.0D));
            }
        }
    }
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        //Fall damage starts at 7 blocks.(Vanilla is 4)
        event.setDistance(event.getDistance() - 3);
        event.setDamageMultiplier(0.5F);
    }
}

package com.sonamorningstar.eternalartifacts.events.curios;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.events.CurioEvents;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import com.sonamorningstar.eternalartifacts.registry.ModSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID)
public class HolyDaggerEvents extends CurioEvents {

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Item holyDagger = ModItems.HOLY_DAGGER.get();
        LivingEntity living = event.getEntityLiving();
        float entityHealth = living.getHealth();
        float entityMaxHealth = living.getMaxHealth();
        float damage = event.getAmount();
        if(!living.level.isClientSide()
                && living instanceof Player player
                && !isOnCooldown(holyDagger, player)
                && (entityHealth <= entityMaxHealth / 4 || entityHealth <= damage)) {

            if( player.hasEffect(MobEffects.ABSORPTION)) { player.removeEffect(MobEffects.ABSORPTION); }

            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 4, true, false));
            addCooldown(holyDagger, player, 1200);
            player.getLevel().playSound(null, getWearerPos(player), ModSounds.HOLY_DAGGER_ACTIVATE.get(),
                    player.getSoundSource(), 1, 1);
        }
    }
}

package com.sonamorningstar.eternalartifacts.item.curio.artifacts;

import com.sonamorningstar.eternalartifacts.item.curio.CurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HolyDaggerItem extends CurioItem {

    public HolyDaggerItem() {
        addListener(LivingHurtEvent.class, this::onEntityHurt);
    }

    public void onEntityHurt(LivingHurtEvent event, LivingEntity entity) {
        float entityHealth = event.getEntityLiving().getHealth();
        float entityMaxHealth = event.getEntityLiving().getMaxHealth();
        if(entityHealth <= entityMaxHealth/5 && !isOnCooldown(this, entity)) {
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 4, true, false));
            if(entity instanceof Player player) {
                player.getCooldowns().addCooldown(this, 1200);
            }
        }
    }

}

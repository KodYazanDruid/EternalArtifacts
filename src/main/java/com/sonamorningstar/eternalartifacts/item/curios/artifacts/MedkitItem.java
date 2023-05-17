package com.sonamorningstar.eternalartifacts.item.curios.artifacts;

import com.sonamorningstar.eternalartifacts.item.curios.CurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class MedkitItem extends CurioItem {

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if(!player.level.isClientSide() && !isOnCooldown(stack.getItem(), player) && player.tickCount % 50 == 0) {
            player.addEffect(
                    new MobEffectInstance(MobEffects.REGENERATION, 55, 0, true, false));
        }
    }
}

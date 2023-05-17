package com.sonamorningstar.eternalartifacts.item.curios;

import com.sonamorningstar.eternalartifacts.item.EternalArtifactsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;

public class CurioItem extends EternalArtifactsItem implements ICurioItem {

    public BlockPos getWearerPos(Player player) {
        return new BlockPos(player.getX(), player.getY(), player.getZ());
    }

    public void addCooldown(Item item, Player player, int cd) {
        player.getCooldowns().addCooldown(item, cd);
    }

    public void removeCooldown(Item item, Player player) {
        player.getCooldowns().removeCooldown(item);
    }

    public void resetCooldown(Item item, Player player, int cd) {
        removeCooldown(item, player);
        addCooldown(item, player, cd);
    }

    public boolean isOnCooldown(Item item, Player player) {
        return player.getCooldowns().isOnCooldown(item);
    }

    protected static boolean hasCurio(Item item, Player player) {
        Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(player, stack -> stack.is(item));
        if (slot.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }


}

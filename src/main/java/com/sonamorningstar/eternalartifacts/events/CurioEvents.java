package com.sonamorningstar.eternalartifacts.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class CurioEvents {

    protected static boolean isOnCooldown(Item item, Player player) { return player.getCooldowns().isOnCooldown(item); }
    protected static void addCooldown(Item item, Player player, int cd) {
        player.getCooldowns().addCooldown(item, cd);
    }
    protected static void removeCooldown(Item item, Player player) {
        player.getCooldowns().removeCooldown(item);
    }
    protected static void resetCooldown(Item item, Player player, int cd){
        removeCooldown(item, player);
        addCooldown(item, player, cd);
        player.getCooldowns().removeCooldown(item);
        player.getCooldowns().addCooldown(item, 120);
    }

    protected static BlockPos getWearerPos(Player player) { return new BlockPos(player.getX(), player.getY(), player.getZ()); }

    protected static boolean hasCurio(Item item, Player player) {
        Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(player, stack -> stack.is(item));
        if (slot.isPresent()) {
            return true;
        }
        return false;
    }
}

package com.sonamorningstar.eternalartifacts.item.tools;

import com.sonamorningstar.eternalartifacts.entity.projectile.CustomWitherSkull;
import com.sonamorningstar.eternalartifacts.item.tiers.WitheringTier;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import com.sonamorningstar.eternalartifacts.utils.RayTraceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WitheringSwordItem extends SwordItem {
    public WitheringSwordItem() {
        super(WitheringTier.WITHERING.getToolTier(), 5, -2.4F, new Properties().tab(ModItems.CREATIVE_TAB));

    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Player player = (Player) entity;
        BlockHitResult traceResult = RayTraceUtil.retrace(player, ClipContext.Fluid.NONE);
        if(player.getAttackStrengthScale(0.5F) > 0.9F
                && !player.level.isClientSide
                && traceResult.getType() == HitResult.Type.MISS) shootSkulls(entity);
        return super.onEntitySwing(stack, entity);
    }

    protected void shootSkulls(LivingEntity entity) {
        Level level = entity.level;

        CustomWitherSkull skull = new CustomWitherSkull(level, entity);
        level.addFreshEntity(skull);
    }
}

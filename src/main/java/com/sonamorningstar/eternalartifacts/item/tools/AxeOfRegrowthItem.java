package com.sonamorningstar.eternalartifacts.item.tools;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModTiers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AxeOfRegrowthItem extends AxeItem {
    protected BlockParticleOption mossBlock = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MOSS_BLOCK.defaultBlockState());
    public AxeOfRegrowthItem() {
        super(ModTiers.REGROWTH.getToolTier(), 5.0F, -3.0F, new Properties().tab(EternalArtifacts.CREATIVE_TAB));
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        ItemStack stack = pContext.getItemInHand();

        if (blockstate.is(Blocks.DIRT)) {
            level.setBlockAndUpdate(blockpos, Blocks.GRASS_BLOCK.defaultBlockState());
            level.playLocalSound(blockpos.getX(), blockpos.getY(), blockpos.getZ(),
                    SoundEvents.MOSS_BREAK, SoundSource.BLOCKS, 1F, 1F, false);
            stack.hurtAndBreak(2, pContext.getPlayer(), e -> e.broadcastBreakEvent(pContext.getHand()));
            for(int i = 0; i < 360; i++) {
                if(i%36 == 0) {
                    level.addParticle(mossBlock, blockpos.getX() + 0.5, blockpos.getY() + 1, blockpos.getZ() + 0.5,
                            Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}

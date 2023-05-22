package com.sonamorningstar.eternalartifacts.compat.tconstruct.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class RegrowthModifier extends NoLevelsModifier implements BlockInteractionModifierHook {
    protected BlockParticleOption mossBlock = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MOSS_BLOCK.defaultBlockState());

    @Override
    public int getPriority() { return 80; }

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) { hookBuilder.addHook(this, TinkerHooks.BLOCK_INTERACT); }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Player player = context.getPlayer();
        if(!tool.isBroken() && player != null) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());

            if (state.is(Blocks.DIRT)) {
                level.setBlockAndUpdate(blockpos, Blocks.GRASS_BLOCK.defaultBlockState());
                level.playLocalSound(blockpos.getX(), blockpos.getY(), blockpos.getZ(),
                        SoundEvents.MOSS_BREAK, SoundSource.BLOCKS, 1F, 1F, false);
                for(int i = 0; i < 360; i++) {
                    if(i%36 == 0) {
                        level.addParticle(mossBlock, blockpos.getX() + 0.5, blockpos.getY() + 1, blockpos.getZ() + 0.5,
                                Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
                    }
                }
                if (ToolDamageUtil.damage(tool, 3, player, context.getItemInHand())) {
                    player.broadcastBreakEvent(source.getSlot(context.getHand()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}

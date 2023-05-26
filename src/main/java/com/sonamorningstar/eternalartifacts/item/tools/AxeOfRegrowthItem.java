package com.sonamorningstar.eternalartifacts.item.tools;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

import static net.minecraftforge.common.ForgeHooks.canEntityDestroy;

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

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        final int DISTANCE = 32;
        Level level = player.level;
        BlockState block = level.getBlockState(pos);
        HashSet<BlockPos> posHashSet = new HashSet<>();

        if(player.level.isClientSide || !block.is(BlockTags.LOGS) || player.isCrouching())
            return super.onBlockStartBreak(itemstack, pos, player);

        if(!level.getBlockState(pos.north()).isAir()
                && !level.getBlockState(pos.south()).isAir()
                && !level.getBlockState(pos.east()).isAir()
                && !level.getBlockState(pos.west()).isAir()
                && !level.getBlockState(pos.north().east()).isAir()
                && !level.getBlockState(pos.north().west()).isAir()
                && !level.getBlockState(pos.south().east()).isAir()
                && !level.getBlockState(pos.south().west()).isAir()) return super.onBlockStartBreak(itemstack, pos, player);

        findBlock(level, pos, posHashSet, BlockTags.LOGS, DISTANCE);

        for(BlockPos p : posHashSet) {
            if(canEntityDestroy(level, p, player)) {
                level.destroyBlock(p, true, player);
                itemstack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.swingingArm));
            }
        }
        return true;
    }

    private void findBlock(Level level, BlockPos pos, HashSet<BlockPos> set, TagKey<Block> tag, int distance) {
        if(level.getBlockState(pos).is(tag) && !isInSet(set, pos) && distance-- > 0){
            set.add(pos);
            addToNeighbors(level, pos, set, tag);
            findBlock(level, pos.above(), set, tag, distance);
            /*
            Iterable<BlockPos> iterable =
                    BlockPos.MutableBlockPos.betweenClosed(
                            new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1),
                            new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1));
            for(BlockPos bp : iterable) {
                if (level.getBlockState(bp).is(tag)) findBlock(level, pos, set, tag, distance);
            }
             */
        }
    }

    private void addToNeighbors(Level level, BlockPos pos, HashSet<BlockPos> set, TagKey<Block> tag) {
        if(level.getBlockState(pos.north()).is(tag) && !isInSet(set, pos)) { set.add(pos.north()); }
        if(level.getBlockState(pos.south()).is(tag) && !isInSet(set, pos)) { set.add(pos.south()); }
        if(level.getBlockState(pos.east()).is(tag) && !isInSet(set, pos)) { set.add(pos.east()); }
        if(level.getBlockState(pos.west()).is(tag) && !isInSet(set, pos)) { set.add(pos.west()); }
        if(level.getBlockState(pos.north().east()).is(tag) && !isInSet(set, pos)) { set.add(pos.north().east()); }
        if(level.getBlockState(pos.north().west()).is(tag) && !isInSet(set, pos)) { set.add(pos.north().west()); }
        if(level.getBlockState(pos.south().east()).is(tag) && !isInSet(set, pos)) { set.add(pos.south().east()); }
        if(level.getBlockState(pos.south().west()).is(tag) && !isInSet(set, pos)) { set.add(pos.south().west()); }
    }

    private boolean isInSet(HashSet<BlockPos> set, BlockPos pos) { return set.contains(pos); }
}

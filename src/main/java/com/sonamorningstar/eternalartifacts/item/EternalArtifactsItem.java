package com.sonamorningstar.eternalartifacts.item;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class EternalArtifactsItem extends Item {
    public EternalArtifactsItem(Properties pProperties) {
        super(pProperties.stacksTo(1).tab(EternalArtifacts.CREATIVE_TAB));
    }

    public EternalArtifactsItem() {
        this(new Properties());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack item, Level world, List<Component> tooltip,
                                TooltipFlag flags) {
        tooltip.add(new TranslatableComponent("tooltip.eternalartifacts.artifact").withStyle(ChatFormatting.AQUA));
    }
}

package com.sonamorningstar.eternalartifacts;

import com.mojang.logging.LogUtils;
import com.sonamorningstar.eternalartifacts.compat.tconstruct.TConstructCompat;
import com.sonamorningstar.eternalartifacts.registry.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(EternalArtifacts.MOD_ID)
public class EternalArtifacts {
    public static final String MOD_ID = "eternalartifacts";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public EternalArtifacts() {
        ModList modList = ModList.get();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModSounds.register(eventBus);
        ModBlocks.register(eventBus);
        ModEntities.register(eventBus);

        eventBus.addListener(this::setup);

        eventBus.addListener(this::curioPresent);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();

        /*
        * Axe of Regrowth
        * Shield of Cthulhu
        * Withering Sword
         */

        if(modList.isLoaded("tconstruct")) { TConstructCompat.register(eventBus);}
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    public void curioPresent(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {
                SlotTypePreset.CURIO,
                SlotTypePreset.CHARM,
        };
        for (SlotTypePreset preset : types) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, ()-> preset.getMessageBuilder().build());
        }
        ModSlotType[] modTypes = {
                ModSlotType.FEET,
                ModSlotType.MAGIC_FEATHER
        };
        for(ModSlotType slot : modTypes) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, ()-> slot.getMessageBuilder().build());
        }
    }

    public static CreativeModeTab CREATIVE_TAB = new CreativeModeTab(EternalArtifacts.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() { return new ItemStack(ModItems.HOLY_DAGGER.get()); }
    };
}
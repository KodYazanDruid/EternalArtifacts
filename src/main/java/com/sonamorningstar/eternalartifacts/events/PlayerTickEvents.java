package com.sonamorningstar.eternalartifacts.events;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID)
public class PlayerTickEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {


    }
}

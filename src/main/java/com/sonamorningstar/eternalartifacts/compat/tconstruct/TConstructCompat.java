package com.sonamorningstar.eternalartifacts.compat.tconstruct;

import net.minecraftforge.eventbus.api.IEventBus;

public class TConstructCompat {

    public static void register(IEventBus bus) { ModModifiers.MODIFIERS.register(bus); }
}

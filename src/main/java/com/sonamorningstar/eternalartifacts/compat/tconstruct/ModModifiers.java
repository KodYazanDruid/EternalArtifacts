package com.sonamorningstar.eternalartifacts.compat.tconstruct;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.compat.tconstruct.modifiers.RegrowthModifier;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class ModModifiers extends AbstractModifierProvider {

    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(EternalArtifacts.MOD_ID);

    public static final StaticModifier<Modifier> REGROWTH = MODIFIERS.register("regrowth", RegrowthModifier::new);

    public ModModifiers(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addModifiers() {

    }

    @Override
    public String getName() {
        return "Eternal Artifacts Modifiers";
    }
}

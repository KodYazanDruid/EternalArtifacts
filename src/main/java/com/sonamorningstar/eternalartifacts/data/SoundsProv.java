package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundsProv extends SoundDefinitionsProvider {
    protected SoundsProv(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, EternalArtifacts.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.HOLY_DAGGER_ACTIVATE.get(),
                definition().subtitle("%s.subtitle.%s"
                        .formatted(EternalArtifacts.MOD_ID, ModSounds.HOLY_DAGGER_ACTIVATE.get().getLocation().getPath())
                ).with(sound(ModSounds.HOLY_DAGGER_ACTIVATE.getId()))
        );

    }
}

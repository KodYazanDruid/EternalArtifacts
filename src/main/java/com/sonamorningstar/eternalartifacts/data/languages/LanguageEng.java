package com.sonamorningstar.eternalartifacts.data.languages;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageEng extends LanguageProvider {
    public LanguageEng(DataGenerator generator, String locale) {
        super(generator, EternalArtifacts.MOD_ID, locale);
    }
    @Override
    protected void addTranslations() {
        add("item.group."+EternalArtifacts.MOD_ID, "Eternal Artifacts");

        add(ModItems.HOLY_DAGGER.get(), "Holy Dagger");
        add(ModItems.MEDKIT.get(), "Medkit");

        add(ModItems.ORANGE.get(), "Orange");
        add(ModItems.PLANT_MATTER.get(), "Plant Matter");

        add(ModBlocks.TINKERING_TABLE.get(), "Tinkering Table");

        add("eternalartifacts.subtitle.holy_dagger_activate", "Holy dagger used.");

        add("tooltip.eternalartifacts.artifact", "Placeholder tooltip.");
    }
}

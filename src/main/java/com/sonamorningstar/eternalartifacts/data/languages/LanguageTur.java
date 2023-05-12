package com.sonamorningstar.eternalartifacts.data.languages;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageTur extends LanguageProvider {
    public LanguageTur(DataGenerator generator, String locale) {
        super(generator, EternalArtifacts.MOD_ID, locale);
    }
    @Override
    protected void addTranslations() {
        add("item.group."+EternalArtifacts.MOD_ID, "Ebedi Eserler");

        add(ModItems.HOLY_DAGGER.get(), "Kutsal Kılıç");
        add(ModItems.MEDKIT.get(), "Medkit");

        add(ModItems.ORANGE.get(), "Portakal");
        add(ModItems.PLANT_MATTER.get(), "Bitki Maddesi");

        add(ModBlocks.TINKERING_TABLE.get(), "Tamirci Masası");


        add("eternalartifacts.subtitle.holy_dagger_activate", "Kutsal kılıç kullanıldı.");

        add("tooltip.eternalartifacts.artifact", "Yertutucu açıklama.");
    }

}
package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageProv extends LanguageProvider {

    private String locale;

    public LanguageProv(DataGenerator generator, String locale) {
        super(generator, EternalArtifacts.MOD_ID, locale);
        this.locale = locale;
    }
    @Override
    protected void addTranslations() {

        switch (locale) {
            case "en_us": {
                add("item.group."+EternalArtifacts.MOD_ID, "Eternal Artifacts");

                add(ModItems.HOLY_DAGGER.get(), "Holy Dagger");
                add(ModItems.FROG_LEGS.get(), "Frog Legs");
                add(ModItems.MEDKIT.get(), "Medkit");

                add(ModItems.ORANGE.get(), "Orange");
                add(ModItems.PLANT_MATTER.get(), "Plant Matter");

                add(ModItems.WITHERING_SWORD.get(), "Withering Sword");

                add(ModBlocks.TINKERING_TABLE.get(), "Tinkering Table");

                add("eternalartifacts.subtitle.holy_dagger_activate", "Holy dagger used.");

                add("tooltip.eternalartifacts.artifact", "Placeholder tooltip.");

                break;
            }
            case "tr_tr": {
                add("item.group."+EternalArtifacts.MOD_ID, "Ebedi Eserler");

                add(ModItems.HOLY_DAGGER.get(), "Kutsal Kılıç");
                add(ModItems.FROG_LEGS.get(), "Kurbağa Bacakları");
                add(ModItems.MEDKIT.get(), "Sağlık Çantası");

                add(ModItems.ORANGE.get(), "Portakal");
                add(ModItems.PLANT_MATTER.get(), "Bitki Maddesi");

                add(ModItems.WITHERING_SWORD.get(), "Çürüyen Kılıç");


                add(ModBlocks.TINKERING_TABLE.get(), "Tamirci Masası");


                add("eternalartifacts.subtitle.holy_dagger_activate", "Kutsal kılıç kullanıldı.");

                add("tooltip.eternalartifacts.artifact", "Yertutucu açıklama.");
                break;
            }
        }
    }

}
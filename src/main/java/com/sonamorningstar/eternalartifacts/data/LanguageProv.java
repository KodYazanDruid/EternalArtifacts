package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.compat.tconstruct.ModModifiers;
import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import com.sonamorningstar.eternalartifacts.registry.ModEntities;
import com.sonamorningstar.eternalartifacts.registry.ModItems;
import com.sonamorningstar.eternalartifacts.registry.ModSlotType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;

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

                add(ModItems.DEMON_EYE_SPAWN_EGG.get(), "Demon Eye Spawn Egg");

                add(ModEntities.DEMON_EYE.get(), "Demon Eye");

                add(ModItems.ORANGE.get(), "Orange");
                add(ModItems.PLANT_MATTER.get(), "Plant Matter");
                add(ModItems.LENS.get(), "Lens");

                add(ModItems.WITHERING_SWORD.get(), "Withering Sword");
                add(ModItems.AXE_OF_REGROWTH.get(), "Axe of Regrowth");

                add(ModBlocks.TINKERING_TABLE.get(), "Tinkering Table");

                add(ModSlotType.FEET.getLangIdentifier(), "Feet");

                add("eternalartifacts.subtitle.holy_dagger_activate", "Holy dagger used.");

                add("tooltip.eternalartifacts.artifact", "Placeholder tooltip.");

                //Tcon
                addModifier(ModModifiers.REGROWTH, "Regrowth", "For the wild!", "Tool can regrow grass from plain dirt!");

                break;
            }
            case "tr_tr": {
                add("item.group."+EternalArtifacts.MOD_ID, "Ebedi Eserler");

                add(ModItems.HOLY_DAGGER.get(), "Kutsal Kılıç");
                add(ModItems.FROG_LEGS.get(), "Kurbağa Bacakları");
                add(ModItems.MEDKIT.get(), "Sağlık Çantası");

                add(ModItems.DEMON_EYE_SPAWN_EGG.get(), "İblis Gözü Çağırma Yumurtası");

                add(ModEntities.DEMON_EYE.get(), "İblis Gözü");

                add(ModItems.ORANGE.get(), "Portakal");
                add(ModItems.PLANT_MATTER.get(), "Bitki Maddesi");
                add(ModItems.LENS.get(), "Lens");

                add(ModItems.WITHERING_SWORD.get(), "Çürüyen Kılıç");
                add(ModItems.AXE_OF_REGROWTH.get(), "Yeşertme Baltası");

                add(ModBlocks.TINKERING_TABLE.get(), "Tamirci Masası");

                add(ModSlotType.FEET.getLangIdentifier(), "Ayak");

                add("eternalartifacts.subtitle.holy_dagger_activate", "Kutsal kılıç kullanıldı.");

                add("tooltip.eternalartifacts.artifact", "Yertutucu açıklama.");

                //Tcon
                addModifier(ModModifiers.REGROWTH, "Yeşertici", "Doğa için!", "Alet basit bir toprağı yeşerterek çim bloğu yapabilir!");

                break;
            }
        }
    }

    public void addModifier(StaticModifier<? extends Modifier> modifier, String name, String flavour, String desc) {
        String id = modifier.getId().getPath();
        add("modifier."+EternalArtifacts.MOD_ID+"." + id, name);
        add("modifier."+EternalArtifacts.MOD_ID+"." + id + ".flavor", flavour);
        add("modifier."+EternalArtifacts.MOD_ID+"." + id + ".description", desc);
    }
}
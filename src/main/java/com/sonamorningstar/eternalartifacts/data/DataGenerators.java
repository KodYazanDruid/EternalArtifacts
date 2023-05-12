package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.data.languages.LanguageEng;
import com.sonamorningstar.eternalartifacts.data.languages.LanguageTur;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = EternalArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            BlockTagsProv blockTags = new BlockTagsProv(generator, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTagsProv(generator, blockTags, helper));
            generator.addProvider(new LootTablesProv(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new ItemModelsProv(generator, helper));
            generator.addProvider(new BlockStatesProv(generator, helper));
            generator.addProvider(new SoundsProv(generator, helper));
            generator.addProvider(new LanguageEng(generator, "en_us"));
            generator.addProvider(new LanguageTur(generator, "tr_tr"));
        }
    }
}

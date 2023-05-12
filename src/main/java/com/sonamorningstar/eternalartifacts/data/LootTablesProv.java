package com.sonamorningstar.eternalartifacts.data;

import com.sonamorningstar.eternalartifacts.registry.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LootTablesProv extends BaseLootTableProvider {
    public LootTablesProv(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.TINKERING_TABLE.get(), createSimpleTable("tinkering_table", ModBlocks.TINKERING_TABLE.get()));
    }
}

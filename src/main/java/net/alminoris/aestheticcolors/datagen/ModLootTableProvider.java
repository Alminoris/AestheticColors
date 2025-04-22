package net.alminoris.aestheticcolors.datagen;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.block.custom.CustomBedBlock;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.enums.BedPart;

public class ModLootTableProvider extends FabricBlockLootTableProvider
{
    public ModLootTableProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void generate()
    {
        for(String name : BlockSetsHelper.COLORS)
        {
            addDrop(ModBlocks.WOOLS.get(name));
            addDrop(ModBlocks.CARPETS.get(name));
            addDrop(ModBlocks.TERRACOTTAS.get(name));
            addDrop(ModBlocks.CONCRETES.get(name));
            addDrop(ModBlocks.CONCRETE_POWDERS.get(name));
            addDrop(ModBlocks.CANDLES.get(name), this::candleDrops);
            addDrop(ModBlocks.CANDLE_CAKES.get(name), candleCakeDrops(ModBlocks.CANDLES.get(name)));
            addDrop(ModBlocks.BEDS.get(name), block -> this.dropsWithProperty(block, CustomBedBlock.PART, BedPart.HEAD));
            addDropWithSilkTouch(ModBlocks.STAINED_GRASSES.get(name));
            addDropWithSilkTouch(ModBlocks.STAINED_GLASS_PANES.get(name));
            addDrop(ModBlocks.SHULKER_BOXES.get(name), this::shulkerBoxDrops);
        }
    }
}
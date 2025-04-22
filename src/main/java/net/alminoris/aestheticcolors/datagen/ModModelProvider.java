package net.alminoris.aestheticcolors.datagen;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.item.ModItems;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.alminoris.aestheticcolors.util.helper.ModJsonHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

public class ModModelProvider extends FabricModelProvider
{    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
    {
        for(String name : BlockSetsHelper.COLORS)
        {
            blockStateModelGenerator.registerWoolAndCarpet(ModBlocks.WOOLS.get(name), ModBlocks.CARPETS.get(name));
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CONCRETES.get(name));
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TERRACOTTAS.get(name));
            blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.CONCRETE_POWDERS.get(name));
            blockStateModelGenerator.registerCandle(ModBlocks.CANDLES.get(name), ModBlocks.CANDLE_CAKES.get(name));
            blockStateModelGenerator.registerGlassPane(ModBlocks.STAINED_GRASSES.get(name), ModBlocks.STAINED_GLASS_PANES.get(name));
            ModJsonHelper.createBedBlockstate(name);
            ModJsonHelper.createShulkerBoxBlockModel(name);
            ModJsonHelper.createShulkerBoxBlockstate(name);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator)
    {
        for(String name : BlockSetsHelper.COLORS)
        {
            itemModelGenerator.register(ModItems.DYES.get(name), Models.GENERATED);
            itemModelGenerator.register(ModBlocks.BEDS.get(name).asItem(), Models.GENERATED);
            itemModelGenerator.register(ModBlocks.SHULKER_BOXES.get(name).asItem(), Models.GENERATED);
        }
    }
}
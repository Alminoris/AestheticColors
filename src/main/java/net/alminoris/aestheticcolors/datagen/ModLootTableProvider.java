package net.alminoris.aestheticcolors.datagen;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider
{
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate()
    {
        for(String name : BlockSetsHelper.COLORS)
        {
            addDrop(ModBlocks.SIMPLE_CARPETS.get(name));
            addDrop(ModBlocks.SIMPLE_CARPET_BLOCKS.get(name));
        }
    }
}
package net.alminoris.aestheticcolors.datagen;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static net.alminoris.aestheticcolors.util.helper.BlockSetsHelper.COLORS;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider
{
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        for (String name : COLORS)
        {
            getOrCreateTagBuilder(BlockTags.WOOL)
                    .add(ModBlocks.WOOLS.get(name));

            getOrCreateTagBuilder(BlockTags.WOOL_CARPETS)
                    .add(ModBlocks.CARPETS.get(name));

            getOrCreateTagBuilder(BlockTags.TERRACOTTA)
                    .add(ModBlocks.TERRACOTTAS.get(name));

            getOrCreateTagBuilder(BlockTags.CANDLES)
                    .add(ModBlocks.CANDLES.get(name));

            getOrCreateTagBuilder(BlockTags.CANDLE_CAKES)
                    .add(ModBlocks.CANDLE_CAKES.get(name));

            getOrCreateTagBuilder(BlockTags.IMPERMEABLE)
                    .add(ModBlocks.STAINED_GRASSES.get(name));

            getOrCreateTagBuilder(BlockTags.BEDS)
                    .add(ModBlocks.BEDS.get(name));

            getOrCreateTagBuilder(BlockTags.SHULKER_BOXES)
                    .add(ModBlocks.SHULKER_BOXES.get(name));
        }
    }
}
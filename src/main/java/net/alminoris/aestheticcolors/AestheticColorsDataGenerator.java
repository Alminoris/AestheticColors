package net.alminoris.aestheticcolors;

import net.alminoris.aestheticcolors.datagen.ModBlockTagProvider;
import net.alminoris.aestheticcolors.datagen.ModLootTableProvider;
import net.alminoris.aestheticcolors.datagen.ModModelProvider;
import net.alminoris.aestheticcolors.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AestheticColorsDataGenerator implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		fabricDataGenerator.addProvider(ModModelProvider::new);
		fabricDataGenerator.addProvider(ModRecipeProvider::new);
		fabricDataGenerator.addProvider(ModLootTableProvider::new);
		fabricDataGenerator.addProvider(ModBlockTagProvider::new);
	}
}
package net.alminoris.aestheticcolors;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.item.ModItemGroups;
import net.alminoris.aestheticcolors.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AestheticColors implements ModInitializer
{
	public static final String MOD_ID = "aestheticcolors";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		ModItemGroups.registerItemGroups();
	}
}
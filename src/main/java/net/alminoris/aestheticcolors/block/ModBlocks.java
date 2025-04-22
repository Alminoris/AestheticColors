package net.alminoris.aestheticcolors.block;

import net.alminoris.aestheticcolors.AestheticColors;
import net.alminoris.aestheticcolors.block.custom.CustomBedBlock;
import net.alminoris.aestheticcolors.block.custom.CustomShulkerBoxBlock;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.Dictionary;
import java.util.Hashtable;

public class ModBlocks
{
    public static final Dictionary<String, Integer> CUSTOM_COLORS = new Hashtable<>()
    {{
        put("beige", 0XF5F5DC);
        put("blue_gray", 0X6699CC);
        put("coral", 0XFF7F50);
        put("emerald_green", 0X50C878);
        put("indigo", 0X4B0082);
        put("cognac", 0X9A463D);
        put("ebony", 0X555D50);
        put("olive", 0X808000);
        put("mint", 0X3EB489);
        put("teal_green", 0X006D5B);
        put("burgundy", 0X800020);
        put("marsala", 0XB57170);
        put("fuchsia", 0XFF00FF);
        put("blue_iris", 0X5A4FCF);
        put("khaki", 0XF0E68C);
        put("aquamarine", 0X7FFFD4);
    }};

    public static final Dictionary<String, Block> CARPETS = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_carpet", new CarpetBlock(AbstractBlock.Settings.copy(Blocks.WHITE_CARPET))));
        }
    }};

    public static final Dictionary<String, Block> WOOLS = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_wool", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL))));
        }
    }};

    public static final Dictionary<String, Block> TERRACOTTAS = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_terracotta", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_TERRACOTTA))));
        }
    }};

    public static final Dictionary<String, Block> CONCRETES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_concrete", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_CONCRETE))));
        }
    }};

    public static final Dictionary<String, Block> CONCRETE_POWDERS = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_concrete_powder", new ConcretePowderBlock(CONCRETES.get(name), AbstractBlock.Settings.copy(Blocks.WHITE_CONCRETE_POWDER))));
        }
    }};

    public static final Dictionary<String, Block> STAINED_GRASSES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_stained_glass", new StainedGlassBlock(DyeColor.byFireworkColor(CUSTOM_COLORS.get(name)), AbstractBlock.Settings.copy(Blocks.WHITE_STAINED_GLASS))));
        }
    }};

    public static final Dictionary<String, Block> STAINED_GLASS_PANES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.byFireworkColor(CUSTOM_COLORS.get(name)), AbstractBlock.Settings.copy(Blocks.WHITE_STAINED_GLASS_PANE))));
        }
    }};

    public static final Dictionary<String, Block> CANDLES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_candle", new CandleBlock(AbstractBlock.Settings.copy(Blocks.WHITE_CANDLE))));
        }
    }};

    public static final Dictionary<String, Block> CANDLE_CAKES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_candle_cake", new CandleCakeBlock(CANDLES.get(name), AbstractBlock.Settings.copy(Blocks.WHITE_CANDLE_CAKE))));
        }
    }};

    public static final Dictionary<String, Block> BEDS = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_bed", new CustomBedBlock(ModDyeColor.byName(name, ModDyeColor.BEIGE), AbstractBlock.Settings.copy(Blocks.WHITE_BED))));
        }
    }};

    public static final Dictionary<String, Block> SHULKER_BOXES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerBlock(name+"_shulker_box", new CustomShulkerBoxBlock(ModDyeColor.byName(name, ModDyeColor.BEIGE), AbstractBlock.Settings.copy(Blocks.WHITE_SHULKER_BOX))));
        }
    }};

    public static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(AestheticColors.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block)
    {
        Registry.register(Registries.ITEM, Identifier.of(AestheticColors.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks()
    {

    }
}
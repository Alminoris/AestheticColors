package net.alminoris.aestheticcolors.item;

import net.alminoris.aestheticcolors.AestheticColors;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Dictionary;
import java.util.Hashtable;

public class ModItems
{
    public static final Dictionary<String, Item> DYES = new Hashtable<>()
    {{
        for(String name : BlockSetsHelper.COLORS)
        {
            put(name, registerItem(name+"_dye", new DyeItem(DyeColor.BLUE, new FabricItemSettings().group(ModItemGroups.ACOLS_TAB))));
        }
    }};

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registry.ITEM, Identifier.of(AestheticColors.MOD_ID, name), item);
    }

    public static void registerItems()
    {

    }
}

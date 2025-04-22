package net.alminoris.aestheticcolors.item;

import net.alminoris.aestheticcolors.AestheticColors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups
{
    public static final ItemGroup ACOLS_TAB = FabricItemGroupBuilder.build(new Identifier(AestheticColors.MOD_ID, "acolstab"),
            () -> new ItemStack(ModItems.DYES.get("indigo")));

    public static void registerItemGroups()
    {

    }
}
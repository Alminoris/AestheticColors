package net.alminoris.aestheticcolors.item;

import net.alminoris.aestheticcolors.AestheticColors;
import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups
{
    public static final ItemGroup ACOLS_TAB = FabricItemGroup.builder(new Identifier(AestheticColors.MOD_ID, "acolstab"))
            .displayName(Text.translatable("itemgroup.acolstab"))
            .icon(() -> new ItemStack(ModItems.DYES.get("indigo"))).entries((displayContext, entries) ->
                    {
                        for(String name : BlockSetsHelper.COLORS)
                        {
                            entries.add(ModItems.DYES.get(name));
                            entries.add(ModBlocks.CARPETS.get(name));
                            entries.add(ModBlocks.WOOLS.get(name));
                            entries.add(ModBlocks.TERRACOTTAS.get(name));
                            entries.add(ModBlocks.CONCRETES.get(name));
                            entries.add(ModBlocks.CONCRETE_POWDERS.get(name));
                            entries.add(ModBlocks.STAINED_GRASSES.get(name));
                            entries.add(ModBlocks.STAINED_GLASS_PANES.get(name));
                            entries.add(ModBlocks.CANDLES.get(name));
                            entries.add(ModBlocks.BEDS.get(name));
                            entries.add(ModBlocks.SHULKER_BOXES.get(name));
                        }
                    }).build();

    public static void registerItemGroups()
    {

    }
}
package net.alminoris.aestheticcolors.datagen;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.item.ModItems;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider
{
    public ModRecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> recipeExporter)
    {
        List<Item> list = new ArrayList<>(List.of(
                Items.BLACK_DYE,
                Items.BLUE_DYE,
                Items.BROWN_DYE,
                Items.CYAN_DYE,
                Items.GRAY_DYE,
                Items.GREEN_DYE,
                Items.LIGHT_BLUE_DYE,
                Items.LIGHT_GRAY_DYE,
                Items.LIME_DYE,
                Items.MAGENTA_DYE,
                Items.ORANGE_DYE,
                Items.PINK_DYE,
                Items.PURPLE_DYE,
                Items.RED_DYE,
                Items.YELLOW_DYE,
                Items.WHITE_DYE
        ));
        List<Item> list2 = new ArrayList<>(List.of(
                Items.BLACK_WOOL,
                Items.BLUE_WOOL,
                Items.BROWN_WOOL,
                Items.CYAN_WOOL,
                Items.GRAY_WOOL,
                Items.GREEN_WOOL,
                Items.LIGHT_BLUE_WOOL,
                Items.LIGHT_GRAY_WOOL,
                Items.LIME_WOOL,
                Items.MAGENTA_WOOL,
                Items.ORANGE_WOOL,
                Items.PINK_WOOL,
                Items.PURPLE_WOOL,
                Items.RED_WOOL,
                Items.YELLOW_WOOL,
                Items.WHITE_WOOL
        ));

        List<Item> list3 = new ArrayList<>(List.of(
                Items.BLACK_CARPET,
                Items.BLUE_CARPET,
                Items.BROWN_CARPET,
                Items.CYAN_CARPET,
                Items.GRAY_CARPET,
                Items.GREEN_CARPET,
                Items.LIGHT_BLUE_CARPET,
                Items.LIGHT_GRAY_CARPET,
                Items.LIME_CARPET,
                Items.MAGENTA_CARPET,
                Items.ORANGE_CARPET,
                Items.PINK_CARPET,
                Items.PURPLE_CARPET,
                Items.RED_CARPET,
                Items.YELLOW_CARPET,
                Items.WHITE_CARPET
        ));

        List<Item> list4 = new ArrayList<>(List.of(
                Items.BLACK_BED,
                Items.BLUE_BED,
                Items.BROWN_BED,
                Items.CYAN_BED,
                Items.GRAY_BED,
                Items.GREEN_BED,
                Items.LIGHT_BLUE_BED,
                Items.LIGHT_GRAY_BED,
                Items.LIME_BED,
                Items.MAGENTA_BED,
                Items.ORANGE_BED,
                Items.PINK_BED,
                Items.PURPLE_BED,
                Items.RED_BED,
                Items.YELLOW_BED,
                Items.WHITE_BED
        ));

        List<Item> list5 = new ArrayList<>(List.of(
                Items.BLACK_SHULKER_BOX,
                Items.BLUE_SHULKER_BOX,
                Items.BROWN_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX,
                Items.GRAY_SHULKER_BOX,
                Items.GREEN_SHULKER_BOX,
                Items.LIGHT_BLUE_SHULKER_BOX,
                Items.LIGHT_GRAY_SHULKER_BOX,
                Items.LIME_SHULKER_BOX,
                Items.MAGENTA_SHULKER_BOX,
                Items.ORANGE_SHULKER_BOX,
                Items.PINK_SHULKER_BOX,
                Items.PURPLE_SHULKER_BOX,
                Items.RED_SHULKER_BOX,
                Items.YELLOW_SHULKER_BOX,
                Items.WHITE_SHULKER_BOX
        ));

        for(String name : BlockSetsHelper.COLORS)
        {
            list.add(ModItems.DYES.get(name));
            list2.add(ModBlocks.WOOLS.get(name).asItem());
            list3.add(ModBlocks.CARPETS.get(name).asItem());
            list4.add(ModBlocks.BEDS.get(name).asItem());
            list5.add(ModBlocks.SHULKER_BOXES.get(name).asItem());
            offerTerracottaDyeingRecipe(recipeExporter, ModBlocks.TERRACOTTAS.get(name), ModItems.DYES.get(name));
            offerConcretePowderDyeingRecipe(recipeExporter, ModBlocks.CONCRETE_POWDERS.get(name), ModItems.DYES.get(name));
            offerStainedGlassDyeingRecipe(recipeExporter, ModBlocks.STAINED_GRASSES.get(name), ModItems.DYES.get(name));
            offerStainedGlassPaneDyeingRecipe(recipeExporter, ModBlocks.STAINED_GLASS_PANES.get(name), ModItems.DYES.get(name));
            offerCandleDyeingRecipe(recipeExporter, ModBlocks.CANDLES.get(name), ModItems.DYES.get(name));
            offerStainedGlassPaneRecipe(recipeExporter, ModBlocks.STAINED_GLASS_PANES.get(name), ModBlocks.STAINED_GRASSES.get(name));
            offerCarpetRecipe(recipeExporter, ModBlocks.CARPETS.get(name), ModBlocks.WOOLS.get(name));
            offerBedRecipe(recipeExporter, ModBlocks.BEDS.get(name), ModBlocks.WOOLS.get(name));
            offerWoolDyeingRecipe(recipeExporter, ModBlocks.WOOLS.get(name), ModItems.DYES.get(name));
            offerCarpetDyeingRecipe(recipeExporter, ModBlocks.CARPETS.get(name), ModItems.DYES.get(name));
            offerBedDyeingRecipe(recipeExporter, ModBlocks.BEDS.get(name), ModItems.DYES.get(name));
        }

        registerDye(recipeExporter, ModItems.DYES.get("indigo"), Items.BLUE_DYE, Items.PURPLE_DYE, Items.BLACK_DYE, Items.MAGENTA_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("beige"), Items.WHITE_DYE, Items.BROWN_DYE, Items.YELLOW_DYE, Items.LIGHT_GRAY_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("coral"), Items.RED_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.MAGENTA_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("emerald_green"), Items.GREEN_DYE, Items.LIME_DYE, Items.CYAN_DYE, Items.WHITE_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("blue_gray"), Items.BLUE_DYE, Items.GRAY_DYE, Items.LIGHT_GRAY_DYE, Items.BLACK_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("cognac"), Items.BROWN_DYE, Items.RED_DYE, Items.ORANGE_DYE, Items.BLACK_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("ebony"), Items.BLACK_DYE, Items.GRAY_DYE, Items.BROWN_DYE, Items.LIGHT_GRAY_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("olive"), Items.GREEN_DYE, Items.YELLOW_DYE, Items.BROWN_DYE, Items.GRAY_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("mint"), Items.LIME_DYE, Items.CYAN_DYE, Items.WHITE_DYE, Items.LIGHT_BLUE_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("teal_green"), Items.GREEN_DYE, Items.CYAN_DYE, Items.BLUE_DYE, Items.LIME_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("burgundy"), Items.RED_DYE, Items.PURPLE_DYE, Items.BROWN_DYE, Items.BLACK_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("marsala"), Items.BROWN_DYE, Items.RED_DYE, Items.PINK_DYE, Items.MAGENTA_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("fuchsia"), Items.PURPLE_DYE, Items.PINK_DYE, Items.MAGENTA_DYE, Items.LIGHT_BLUE_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("blue_iris"), Items.BLUE_DYE, Items.PURPLE_DYE, Items.LIGHT_BLUE_DYE, Items.MAGENTA_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("khaki"), Items.YELLOW_DYE, Items.BROWN_DYE, Items.WHITE_DYE, Items.LIGHT_GRAY_DYE);
        registerDye(recipeExporter, ModItems.DYES.get("aquamarine"), Items.CYAN_DYE, Items.LIGHT_BLUE_DYE, Items.LIME_DYE, Items.WHITE_DYE);
    }

    private static void registerDye(Consumer<RecipeJsonProvider> recipeExporter, Item output, Item input1, Item input2, Item input3, Item input4)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, 4)
                .input(input1)
                .input(input2)
                .input(input3)
                .input(input4)
                .criterion(hasItem(input1), conditionsFromItem(input1))
                .criterion(hasItem(input2), conditionsFromItem(input2))
                .criterion(hasItem(input3), conditionsFromItem(input3))
                .criterion(hasItem(input4), conditionsFromItem(input4))
                .group("dyes")
                .offerTo(recipeExporter);
    }
}
package net.alminoris.aestheticcolors.block.entity;

import net.alminoris.aestheticcolors.AestheticColors;
import net.alminoris.aestheticcolors.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities
{
    public static final BlockEntityType<CustomBedBlockEntity> CUSTOM_BED =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AestheticColors.MOD_ID, "custom_bed_be"),
                    FabricBlockEntityTypeBuilder.create(CustomBedBlockEntity::new,
                            ModBlocks.BEDS.get("beige"),
                            ModBlocks.BEDS.get("blue_gray"),
                            ModBlocks.BEDS.get("coral"),
                            ModBlocks.BEDS.get("emerald_green"),
                            ModBlocks.BEDS.get("indigo"),
                            ModBlocks.BEDS.get("cognac"),
                            ModBlocks.BEDS.get("ebony"),
                            ModBlocks.BEDS.get("olive"),
                            ModBlocks.BEDS.get("mint"),
                            ModBlocks.BEDS.get("teal_green"),
                            ModBlocks.BEDS.get("burgundy"),
                            ModBlocks.BEDS.get("marsala"),
                            ModBlocks.BEDS.get("fuchsia"),
                            ModBlocks.BEDS.get("blue_iris"),
                            ModBlocks.BEDS.get("khaki"),
                            ModBlocks.BEDS.get("aquamarine")).build());

    public static final BlockEntityType<CustomShulkerBoxBlockEntity> CUSTOM_SHULKER_BOX =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AestheticColors.MOD_ID, "custom_shulker_box_be"),
                    FabricBlockEntityTypeBuilder.create(CustomShulkerBoxBlockEntity::new,
                            ModBlocks.SHULKER_BOXES.get("indigo"),
                            ModBlocks.SHULKER_BOXES.get("beige"),
                            ModBlocks.SHULKER_BOXES.get("coral"),
                            ModBlocks.SHULKER_BOXES.get("emerald_green"),
                            ModBlocks.SHULKER_BOXES.get("blue_gray"),
                            ModBlocks.SHULKER_BOXES.get("cognac"),
                            ModBlocks.SHULKER_BOXES.get("ebony"),
                            ModBlocks.SHULKER_BOXES.get("olive"),
                            ModBlocks.SHULKER_BOXES.get("mint"),
                            ModBlocks.SHULKER_BOXES.get("teal_green"),
                            ModBlocks.SHULKER_BOXES.get("burgundy"),
                            ModBlocks.SHULKER_BOXES.get("marsala"),
                            ModBlocks.SHULKER_BOXES.get("fuchsia"),
                            ModBlocks.SHULKER_BOXES.get("blue_iris"),
                            ModBlocks.SHULKER_BOXES.get("khaki"),
                            ModBlocks.SHULKER_BOXES.get("aquamarine")).build());

    public static void registerBlockEntities()
    {

    }
}

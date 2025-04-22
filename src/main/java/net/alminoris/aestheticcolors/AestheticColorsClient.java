package net.alminoris.aestheticcolors;

import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.block.entity.ModBlockEntities;
import net.alminoris.aestheticcolors.block.entity.renderer.CustomBedBlockEntityRenderer;
import net.alminoris.aestheticcolors.block.entity.renderer.CustomShulkerBoxBlockEntityRenderer;
import net.alminoris.aestheticcolors.util.helper.BlockSetsHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class AestheticColorsClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        for(String name : BlockSetsHelper.COLORS)
        {
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STAINED_GRASSES.get(name), RenderLayer.getTranslucent());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STAINED_GLASS_PANES.get(name), RenderLayer.getTranslucent());
        }

        BlockEntityRendererFactories.register(ModBlockEntities.CUSTOM_BED, CustomBedBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntityRenderer::new);
    }
}
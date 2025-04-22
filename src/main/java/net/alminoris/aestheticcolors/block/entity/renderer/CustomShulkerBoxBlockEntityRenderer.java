package net.alminoris.aestheticcolors.block.entity.renderer;

import net.alminoris.aestheticcolors.block.custom.CustomShulkerBoxBlock;
import net.alminoris.aestheticcolors.block.entity.CustomShulkerBoxBlockEntity;
import net.alminoris.aestheticcolors.client.render.ModTexturedRenderLayers;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class CustomShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<CustomShulkerBoxBlockEntity>
{
    private final ShulkerEntityModel<?> model;

    public CustomShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
    {
        this.model = new ShulkerEntityModel<>(ctx.getLayerModelPart(EntityModelLayers.SHULKER));
    }

    public void render(CustomShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j)
    {
        Direction direction = Direction.UP;
        if (shulkerBoxBlockEntity.hasWorld())
        {
            BlockState blockState = shulkerBoxBlockEntity.getWorld().getBlockState(shulkerBoxBlockEntity.getPos());
            if (blockState.getBlock() instanceof CustomShulkerBoxBlock)
            {
                direction = blockState.get(CustomShulkerBoxBlock.FACING);
            }
        }

        ModDyeColor dyeColor = shulkerBoxBlockEntity.getColor();
        SpriteIdentifier spriteIdentifier;
        if (dyeColor == null)
        {
            spriteIdentifier = ModTexturedRenderLayers.SHULKER_TEXTURE_ID;
        }
        else
        {
            spriteIdentifier = (SpriteIdentifier)ModTexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(dyeColor.getId());
        }

        matrixStack.push();
        matrixStack.translate(0.5F, 0.5F, 0.5F);
        float g = 0.9995F;
        matrixStack.scale(0.9995F, 0.9995F, 0.9995F);
        matrixStack.multiply(direction.getRotationQuaternion());
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        matrixStack.translate(0.0F, -1.0F, 0.0F);
        ModelPart modelPart = this.model.getLid();
        modelPart.setPivot(0.0F, 24.0F - shulkerBoxBlockEntity.getAnimationProgress(f) * 0.5F * 16.0F, 0.0F);
        modelPart.yaw = 270.0F * shulkerBoxBlockEntity.getAnimationProgress(f) * (float) (Math.PI / 180.0);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull);
        this.model.render(matrixStack, vertexConsumer, i, j);
        matrixStack.pop();
    }
}
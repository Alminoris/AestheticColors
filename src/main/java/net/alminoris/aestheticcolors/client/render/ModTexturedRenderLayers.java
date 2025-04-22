package net.alminoris.aestheticcolors.client.render;

import com.google.common.collect.ImmutableList;
import net.alminoris.aestheticcolors.AestheticColors;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers
{
    public static final Identifier BEDS_ATLAS_TEXTURE = Identifier.ofVanilla("textures/atlas/beds.png");
    private static final RenderLayer BEDS_RENDER_LAYER = RenderLayer.getEntitySolid(BEDS_ATLAS_TEXTURE);
    public static final Identifier SHULKER_BOXES_ATLAS_TEXTURE = Identifier.ofVanilla("textures/atlas/shulker_boxes.png");
    private static final RenderLayer SHULKER_BOXES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(SHULKER_BOXES_ATLAS_TEXTURE);

    public static RenderLayer getBeds() {
        return BEDS_RENDER_LAYER;
    }

    public static final SpriteIdentifier[] BED_TEXTURES = (SpriteIdentifier[]) Arrays.stream(ModDyeColor.values())
            .sorted(Comparator.comparingInt(ModDyeColor::getId))
            .map(color -> new SpriteIdentifier(BEDS_ATLAS_TEXTURE, Identifier.of(AestheticColors.MOD_ID,"entity/bed/" + color.getName())))
            .toArray(SpriteIdentifier[]::new);

    public static final SpriteIdentifier SHULKER_TEXTURE_ID = new SpriteIdentifier(SHULKER_BOXES_ATLAS_TEXTURE, Identifier.ofVanilla("entity/shulker/shulker"));

    public static final List<SpriteIdentifier> COLORED_SHULKER_BOXES_TEXTURES = (List<SpriteIdentifier>) Stream.of(
                    "indigo", "beige", "coral", "emerald_green", "blue_gray", "cognac", "ebony", "olive",
                    "mint", "teal_green", "burgundy", "marsala", "fuchsia", "blue_iris", "khaki", "aquamarine")
            .map(colorName -> new SpriteIdentifier(SHULKER_BOXES_ATLAS_TEXTURE, Identifier.of(AestheticColors.MOD_ID, "entity/shulker/shulker_" + colorName)))
            .collect(ImmutableList.toImmutableList());
}

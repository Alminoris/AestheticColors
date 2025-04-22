package net.alminoris.aestheticcolors.util;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum ModDyeColor implements StringIdentifiable
{
    INDIGO(0, "indigo", 16119285, ModMapColor.INDIGO),
    BEIGE(1, "beige", 6724044, ModMapColor.BEIGE),
    CORAL(2, "coral", 16744272, ModMapColor.CORAL),
    EMERALD_GREEN(3, "emerald_green", 5289288, ModMapColor.EMERALD_GREEN),
    BLUE_GRAY(4, "blue_gray", 4915330, ModMapColor.BLUE_GRAY),
    COGNAC(5, "cognac", 10037757, ModMapColor.COGNAC),
    EBONY(6, "ebony", 5594064, ModMapColor.EBONY),
    OLIVE(7, "olive", 8421376, ModMapColor.OLIVE),
    MINT(8, "mint", 4115977, ModMapColor.MINT),
    TEAL_GREEN(9, "teal_green", 28027, ModMapColor.TEAL_GREEN),
    BURGUNDY(10, "burgundy", 8388736, ModMapColor.BURGUNDY),
    MARSALA(11, "marsala", 11838192, ModMapColor.MARSALA),
    FUCHSIA(12, "fuchsia", 16711935, ModMapColor.FUCHSIA),
    BLUE_IRIS(13, "blue_iris", 5917903, ModMapColor.BLUE_IRIS),
    KHAKI(14, "khaki", 15787660, ModMapColor.KHAKI),
    AQUAMARINE(15, "aquamarine", 8388564, ModMapColor.AQUAMARINE);

    private static final IntFunction<ModDyeColor> BY_ID = ValueLists.createIdToValueFunction(ModDyeColor::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final StringIdentifiable.Codec<ModDyeColor> CODEC = StringIdentifiable.createCodec(ModDyeColor::values);
    private final int id;
    private final String name;
    private final ModMapColor mapColor;
    private final float[] colorComponents;

    ModDyeColor(int id, String name, int color, ModMapColor mapColor)
    {
        this.id = id;
        this.name = name;
        this.mapColor = mapColor;
        int j = (color & 16711680) >> 16;
        int k = (color & '\uff00') >> 8;
        int l = (color & 255) >> 0;
        this.colorComponents = new float[]{(float)j / 255.0F, (float)k / 255.0F, (float)l / 255.0F};
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float[] getColorComponents() {
        return this.colorComponents;
    }

    public ModMapColor getMapColor() {
        return this.mapColor;
    }

    public static ModDyeColor byId(int id) {
        return (ModDyeColor)BY_ID.apply(id);
    }

    @Nullable
    @Contract("_,!null->!null;_,null->_")
    public static ModDyeColor byName(String name, @Nullable ModDyeColor defaultColor)
    {
        ModDyeColor dyeColor = (ModDyeColor)CODEC.byId(name);
        return dyeColor != null ? dyeColor : defaultColor;
    }

    public String toString() {
        return this.name;
    }

    public String asString()
    {
        return this.name;
    }
}
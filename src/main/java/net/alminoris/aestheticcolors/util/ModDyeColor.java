package net.alminoris.aestheticcolors.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum ModDyeColor implements StringIdentifiable
{
    INDIGO(0, "indigo", ModMapColor.INDIGO),
    BEIGE(1, "beige", ModMapColor.BEIGE),
    CORAL(2, "coral", ModMapColor.CORAL),
    EMERALD_GREEN(3, "emerald_green", ModMapColor.EMERALD_GREEN),
    BLUE_GRAY(4, "blue_gray", ModMapColor.BLUE_GRAY),
    COGNAC(5, "cognac", ModMapColor.COGNAC),
    EBONY(6, "ebony", ModMapColor.EBONY),
    OLIVE(7, "olive", ModMapColor.OLIVE),
    MINT(8, "mint", ModMapColor.MINT),
    TEAL_GREEN(9, "teal_green", ModMapColor.TEAL_GREEN),
    BURGUNDY(10, "burgundy", ModMapColor.BURGUNDY),
    MARSALA(11, "marsala", ModMapColor.MARSALA),
    FUCHSIA(12, "fuchsia", ModMapColor.FUCHSIA),
    BLUE_IRIS(13, "blue_iris", ModMapColor.BLUE_IRIS),
    KHAKI(14, "khaki", ModMapColor.KHAKI),
    AQUAMARINE(15, "aquamarine", ModMapColor.AQUAMARINE);

    private static final IntFunction<ModDyeColor> BY_ID = ValueLists.createIdToValueFunction(ModDyeColor::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final StringIdentifiable.EnumCodec<ModDyeColor> CODEC = StringIdentifiable.createCodec(ModDyeColor::values);
    public static final PacketCodec<ByteBuf, ModDyeColor> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModDyeColor::getId);
    private final int id;
    private final String name;
    private final ModMapColor mapColor;
    
    ModDyeColor(final int id, final String name, final ModMapColor mapColor)
    {
        this.id = id;
        this.name = name;
        this.mapColor = mapColor;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public ModMapColor getModMapColor() {
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

    @Override
    public String asString()
    {
        return this.name;
    }
}

package net.alminoris.aestheticcolors.util;

import com.google.common.base.Preconditions;

public class ModMapColor
{
    private static final ModMapColor[] COLORS = new ModMapColor[64];
    public static final ModMapColor CLEAR = new ModMapColor(0, 0);
    public static final ModMapColor INDIGO = new ModMapColor(1, 16119285);
    public static final ModMapColor BEIGE = new ModMapColor(2, 6724044);
    public static final ModMapColor CORAL = new ModMapColor(3, 16744272);
    public static final ModMapColor EMERALD_GREEN = new ModMapColor(4, 5289288);
    public static final ModMapColor BLUE_GRAY = new ModMapColor(5, 4915330);
    public static final ModMapColor COGNAC = new ModMapColor(6, 10037757);
    public static final ModMapColor EBONY = new ModMapColor(7, 5594064);
    public static final ModMapColor OLIVE = new ModMapColor(8, 8421376);
    public static final ModMapColor MINT = new ModMapColor(9, 4115977);
    public static final ModMapColor TEAL_GREEN = new ModMapColor(10, 28027);
    public static final ModMapColor BURGUNDY = new ModMapColor(11, 8388736);
    public static final ModMapColor MARSALA = new ModMapColor(12, 11838192);
    public static final ModMapColor FUCHSIA = new ModMapColor(13, 16711935);
    public static final ModMapColor BLUE_IRIS = new ModMapColor(14, 5917903);
    public static final ModMapColor KHAKI = new ModMapColor(15, 15787660);
    public static final ModMapColor AQUAMARINE = new ModMapColor(16, 8388564);

    public final int color;
    public final int id;

    private ModMapColor(int id, int color)
    {
        if (id >= 0 && id <= 63)
        {
            this.id = id;
            this.color = color;
            COLORS[id] = this;
        }
        else
        {
            throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
        }
    }

    public int getRenderColor(ModMapColor.Brightness brightness)
    {

        if (this == CLEAR)
        {
            return 0;
        }
        else
        {
            int i = brightness.brightness;
            int j = (this.color >> 16 & 0xFF) * i / 255;
            int k = (this.color >> 8 & 0xFF) * i / 255;
            int l = (this.color & 0xFF) * i / 255;
            return 0xFF000000 | l << 16 | k << 8 | j;
        }
    }

    public static ModMapColor get(int id)
    {
        Preconditions.checkPositionIndex(id, COLORS.length, "material id");
        return getUnchecked(id);
    }

    private static ModMapColor getUnchecked(int id)
    {
        ModMapColor mapColor = COLORS[id];
        return mapColor != null ? mapColor : CLEAR;
    }

    public static int getRenderColor(int colorByte)
    {
        int i = colorByte & 0xFF;
        return getUnchecked(i >> 2).getRenderColor(ModMapColor.Brightness.get(i & 3));
    }

    public byte getRenderColorByte(ModMapColor.Brightness brightness)
    {
        return (byte)(this.id << 2 | brightness.id & 3);
    }

    public static enum Brightness
    {
        LOW(0, 180),
        NORMAL(1, 220),
        HIGH(2, 255),
        LOWEST(3, 135);

        private static final ModMapColor.Brightness[] VALUES = new ModMapColor.Brightness[]{LOW, NORMAL, HIGH, LOWEST};
        public final int id;
        public final int brightness;

        private Brightness(final int id, final int brightness)
        {
            this.id = id;
            this.brightness = brightness;
        }

        public static ModMapColor.Brightness validateAndGet(int id)
        {
            Preconditions.checkPositionIndex(id, VALUES.length, "brightness id");
            return get(id);
        }

        static ModMapColor.Brightness get(int id) {
            return VALUES[id];
        }
    }
}

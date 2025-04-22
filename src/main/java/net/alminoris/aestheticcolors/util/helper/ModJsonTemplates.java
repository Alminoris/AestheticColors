package net.alminoris.aestheticcolors.util.helper;

public class ModJsonTemplates
{
    public static final String BED_BLOCKSTATE = """
            {
              "variants": {
                "": {
                  "model": "aestheticcolors:block/bed"
                }
              }
            }
            """;

    public static final String SHULKER_BOX_BLOCKSTATE = """
            {
              "variants": {
                "": {
                  "model": "aestheticcolors:block/NAME_shulker_box"
                }
              }
            }
            """;

    public static final String SHULKER_BOX_BLOCK_MODEL = """
            {
              "textures": {
                "particle": "aestheticcolors:block/NAME_shulker_box"
              }
            }
            """;
}

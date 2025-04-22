package net.alminoris.aestheticcolors.util.helper;

import net.alminoris.aestheticcolors.AestheticColors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModJsonHelper
{
    public static void createBedBlockstate(String colorName)
    {
        String projectPath = System.getProperty("user.dir");

        String filePath = projectPath.replace("build\\datagen", "src\\main\\resources") + "/assets/"+ AestheticColors.MOD_ID+"/blockstates";

        File directory = new File(filePath);
        if (!directory.exists())
            directory.mkdirs();

        String fileName = colorName + "_bed.json";
        File modelFile = new File(directory, fileName);

        String jsonContent = ModJsonTemplates.BED_BLOCKSTATE;
        jsonContent = jsonContent.replace("NAME", colorName);

        try (FileWriter writer = new FileWriter(modelFile))
        {
            writer.write(jsonContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void createShulkerBoxBlockstate(String colorName)
    {
        String projectPath = System.getProperty("user.dir");

        String filePath = projectPath.replace("build\\datagen", "src\\main\\resources") + "/assets/"+ AestheticColors.MOD_ID+"/blockstates";

        File directory = new File(filePath);
        if (!directory.exists())
            directory.mkdirs();

        String fileName = colorName + "_shulker_box.json";
        File modelFile = new File(directory, fileName);

        String jsonContent = ModJsonTemplates.SHULKER_BOX_BLOCKSTATE;
        jsonContent = jsonContent.replace("NAME", colorName);

        try (FileWriter writer = new FileWriter(modelFile))
        {
            writer.write(jsonContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void createShulkerBoxBlockModel(String colorName)
    {
        String projectPath = System.getProperty("user.dir");

        String filePath = projectPath.replace("build\\datagen", "src\\main\\resources") + "/assets/"+ AestheticColors.MOD_ID+"/models/block";

        File directory = new File(filePath);
        if (!directory.exists())
            directory.mkdirs();

        String fileName = colorName + "_shulker_box.json";
        File modelFile = new File(directory, fileName);

        String jsonContent = ModJsonTemplates.SHULKER_BOX_BLOCK_MODEL;
        jsonContent = jsonContent.replace("NAME", colorName);

        try (FileWriter writer = new FileWriter(modelFile))
        {
            writer.write(jsonContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
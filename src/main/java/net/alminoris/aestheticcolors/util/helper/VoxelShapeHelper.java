package net.alminoris.aestheticcolors.util.helper;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.List;

public class VoxelShapeHelper
{
    public static VoxelShape rotateShape(List<Box> boxes, Direction facing)
    {
        List<VoxelShape> rotatedShapes = new ArrayList<>();

        for (Box originalBox : boxes)
        {
            VoxelShape rotatedShape;

            // Extract original box coordinates
            double minX = originalBox.minX;
            double minY = originalBox.minY;
            double minZ = originalBox.minZ;
            double maxX = originalBox.maxX;
            double maxY = originalBox.maxY;
            double maxZ = originalBox.maxZ;

            // Apply rotation based on the facing direction
            switch (facing)
            {
                case NORTH:
                    rotatedShape = VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
                    break;
                case SOUTH:
                    rotatedShape = VoxelShapes.cuboid(
                            1 - maxX, minY, 1 - maxZ,
                            1 - minX, maxY, 1 - minZ
                    );
                    break;
                case WEST:
                    rotatedShape = VoxelShapes.cuboid(
                            minZ, minY, 1 - maxX,
                            maxZ, maxY, 1 - minX
                    );
                    break;
                case EAST:
                    rotatedShape = VoxelShapes.cuboid(
                            1 - maxZ, minY, minX,
                            1 - minZ, maxY, maxX
                    );
                    break;
                default:
                    rotatedShape = VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
                    break;
            }

            // Validate and correct rotated shape if needed
            rotatedShape = ensureValidBox(rotatedShape);
            rotatedShapes.add(rotatedShape);
        }

        // Combine all rotated parts into a single shape
        VoxelShape finalShape = VoxelShapes.empty();
        for (VoxelShape shape : rotatedShapes)
        {
            finalShape = VoxelShapes.union(finalShape, shape);
        }

        return finalShape;
    }

    private static VoxelShape ensureValidBox(VoxelShape shape)
    {
        Box box = shape.getBoundingBox();

        double minX = Math.min(box.minX, box.maxX);
        double maxX = Math.max(box.minX, box.maxX);
        double minY = Math.min(box.minY, box.maxY);
        double maxY = Math.max(box.minY, box.maxY);
        double minZ = Math.min(box.minZ, box.maxZ);
        double maxZ = Math.max(box.minZ, box.maxZ);

        return VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

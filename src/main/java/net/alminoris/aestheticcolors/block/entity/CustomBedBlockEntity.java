package net.alminoris.aestheticcolors.block.entity;

import net.alminoris.aestheticcolors.block.custom.CustomBedBlock;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlockEntity extends BlockEntity
{
    private ModDyeColor color;

    public CustomBedBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CUSTOM_BED, pos, state);
        this.color = ((CustomBedBlock)state.getBlock()).getColor();
    }

    public CustomBedBlockEntity(BlockPos pos, BlockState state, ModDyeColor color)
    {
        super(ModBlockEntities.CUSTOM_BED, pos, state);
        this.color = color;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public ModDyeColor getColor() {
        return this.color;
    }

    public void setColor(ModDyeColor color) {
        this.color = color;
    }
}
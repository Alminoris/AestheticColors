package net.alminoris.aestheticcolors.block.custom;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.block.entity.CustomShulkerBoxBlockEntity;
import net.alminoris.aestheticcolors.block.entity.ModBlockEntities;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomShulkerBoxBlock extends BlockWithEntity 
{
    public static final MapCodec<CustomShulkerBoxBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(ModDyeColor.CODEC.optionalFieldOf("color").forGetter(block -> Optional.ofNullable(block.color)), createSettingsCodec())
                    .apply(instance, (color, settings) -> new CustomShulkerBoxBlock((ModDyeColor)color.orElse(null), settings))
    );
    private static final Text UNKNOWN_CONTENTS_TEXT = Text.translatable("container.shulkerBox.unknownContents");
    private static final float field_41075 = 1.0F;
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, VoxelShape> SIDES_SHAPES = Util.make(Maps.newEnumMap(Direction.class), map -> {
        map.put(Direction.NORTH, NORTH_SHAPE);
        map.put(Direction.EAST, EAST_SHAPE);
        map.put(Direction.SOUTH, SOUTH_SHAPE);
        map.put(Direction.WEST, WEST_SHAPE);
        map.put(Direction.UP, UP_SHAPE);
        map.put(Direction.DOWN, DOWN_SHAPE);
    });
    public static final EnumProperty<Direction> FACING = FacingBlock.FACING;
    public static final Identifier CONTENTS_DYNAMIC_DROP_ID = Identifier.ofVanilla("contents");
    @Nullable
    private final ModDyeColor color;

    @Override
    public MapCodec<CustomShulkerBoxBlock> getCodec()
    {
        return CODEC;
    }

    public CustomShulkerBoxBlock(@Nullable ModDyeColor color, AbstractBlock.Settings settings)
    {
        super(settings);
        this.color = color;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomShulkerBoxBlockEntity(this.color, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return validateTicker(type, ModBlockEntities.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntity::tick);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (world.isClient)
        {
            return ActionResult.SUCCESS;
        }
        else if (player.isSpectator())
        {
            return ActionResult.CONSUME;
        }
        else if (world.getBlockEntity(pos) instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            if (canOpen(state, world, pos, shulkerBoxBlockEntity))
            {
                player.openHandledScreen(shulkerBoxBlockEntity);
                player.incrementStat(Stats.OPEN_SHULKER_BOX);
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }

            return ActionResult.CONSUME;
        }
        else
        {
            return ActionResult.PASS;
        }
    }

    private static boolean canOpen(BlockState state, World world, BlockPos pos, CustomShulkerBoxBlockEntity entity)
    {
        if (entity.getAnimationStage() != CustomShulkerBoxBlockEntity.AnimationStage.CLOSED)
        {
            return true;
        }
        else
        {
            Box box = ShulkerEntity.calculateBoundingBox(1.0F, state.get(FACING), 0.0F, 0.5F).offset(pos).contract(1.0E-6);
            return world.isSpaceEmpty(box);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(FACING, ctx.getSide());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            if (!world.isClient && player.isCreative() && !shulkerBoxBlockEntity.isEmpty())
            {
                ItemStack itemStack = getItemStack(this.getColor());
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                shulkerBoxBlockEntity.generateLoot(player);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder)
    {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            builder = builder.addDynamicDrop(CONTENTS_DYNAMIC_DROP_ID, lootConsumer ->
            {
                for (int i = 0; i < shulkerBoxBlockEntity.size(); i++)
                {
                    lootConsumer.accept(shulkerBoxBlockEntity.getStack(i));
                }
            });
        }

        return super.getDroppedStacks(state, builder);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
    {
        if (!state.isOf(newState.getBlock()))
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.onStateReplaced(state, world, pos, newState, moved);
            if (blockEntity instanceof CustomShulkerBoxBlockEntity)
            {
                world.updateComparators(pos, state.getBlock());
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options)
    {
        super.appendTooltip(stack, context, tooltip, options);
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT))
        {
            tooltip.add(UNKNOWN_CONTENTS_TEXT);
        }

        int i = 0;
        int j = 0;

        for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
            j++;
            if (i <= 4) {
                i++;
                tooltip.add(Text.translatable("container.shulkerBox.itemCount", itemStack.getName(), itemStack.getCount()));
            }
        }

        if (j - i > 0) {
            tooltip.add(Text.translatable("container.shulkerBox.more", j - i).formatted(Formatting.ITALIC));
        }
    }

    @Override
    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity && !shulkerBoxBlockEntity.suffocates())
        {
            return (VoxelShape)SIDES_SHAPES.get(((Direction)state.get(FACING)).getOpposite());
        }

        return VoxelShapes.fullCube();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof CustomShulkerBoxBlockEntity ? VoxelShapes.cuboid(((CustomShulkerBoxBlockEntity)blockEntity).getBoundingBox(state)) : VoxelShapes.fullCube();
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, ModBlockEntities.CUSTOM_SHULKER_BOX).ifPresent(blockEntity -> blockEntity.setStackNbt(itemStack, world.getRegistryManager()));
        return itemStack;
    }

    @Nullable
    public static ModDyeColor getColor(Item item) {
        return getColor(Block.getBlockFromItem(item));
    }

    @Nullable
    public static ModDyeColor getColor(Block block)
    {
        return block instanceof CustomShulkerBoxBlock ? ((CustomShulkerBoxBlock)block).getColor() : null;
    }

    public static Block get(@Nullable ModDyeColor dyeColor)
    {
        if (dyeColor == null)
        {
            return Blocks.SHULKER_BOX;
        }
        else
        {
            return switch (dyeColor)
            {
                case ModDyeColor.BEIGE -> ModBlocks.SHULKER_BOXES.get("beige");
                case ModDyeColor.BLUE_GRAY -> ModBlocks.SHULKER_BOXES.get("blue_gray");
                case ModDyeColor.CORAL -> ModBlocks.SHULKER_BOXES.get("coral");
                case ModDyeColor.EMERALD_GREEN -> ModBlocks.SHULKER_BOXES.get("emerald_green");
                case ModDyeColor.INDIGO -> ModBlocks.SHULKER_BOXES.get("indigo");
                case ModDyeColor.COGNAC -> ModBlocks.SHULKER_BOXES.get("cognac");
                case ModDyeColor.EBONY -> ModBlocks.SHULKER_BOXES.get("ebony");
                case ModDyeColor.OLIVE -> ModBlocks.SHULKER_BOXES.get("olive");
                case ModDyeColor.MINT -> ModBlocks.SHULKER_BOXES.get("mint");
                case ModDyeColor.TEAL_GREEN -> ModBlocks.SHULKER_BOXES.get("teal_green");
                case ModDyeColor.BURGUNDY -> ModBlocks.SHULKER_BOXES.get("burgundy");
                case ModDyeColor.MARSALA -> ModBlocks.SHULKER_BOXES.get("marsala");
                case ModDyeColor.FUCHSIA -> ModBlocks.SHULKER_BOXES.get("fuchsia");
                case ModDyeColor.BLUE_IRIS -> ModBlocks.SHULKER_BOXES.get("blue_iris");
                case ModDyeColor.KHAKI -> ModBlocks.SHULKER_BOXES.get("khaki");
                case ModDyeColor.AQUAMARINE -> ModBlocks.SHULKER_BOXES.get("aquamarine");
            };
        }
    }

    @Nullable
    public ModDyeColor getColor() {
        return this.color;
    }

    public static ItemStack getItemStack(@Nullable ModDyeColor color) {
        return new ItemStack(get(color));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}
package net.alminoris.aestheticcolors.block.custom;

import com.google.common.collect.Maps;
import net.alminoris.aestheticcolors.block.ModBlocks;
import net.alminoris.aestheticcolors.block.entity.CustomShulkerBoxBlockEntity;
import net.alminoris.aestheticcolors.block.entity.ModBlockEntities;
import net.alminoris.aestheticcolors.util.ModDyeColor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomShulkerBoxBlock extends BlockWithEntity
{
    private static final float field_41075 = 1.0F;
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, VoxelShape> SIDES_SHAPES = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH_SHAPE);
        map.put(Direction.EAST, EAST_SHAPE);
        map.put(Direction.SOUTH, SOUTH_SHAPE);
        map.put(Direction.WEST, WEST_SHAPE);
        map.put(Direction.UP, UP_SHAPE);
        map.put(Direction.DOWN, DOWN_SHAPE);
    });
    public static final EnumProperty<Direction> FACING;
    public static final Identifier CONTENTS_DYNAMIC_DROP_ID;
    @Nullable
    private final ModDyeColor color;

    public CustomShulkerBoxBlock(@Nullable ModDyeColor color, AbstractBlock.Settings settings)
    {
        super(settings);
        this.color = color;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.UP));
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new CustomShulkerBoxBlockEntity(this.color, pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return checkType(type, ModBlockEntities.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntity::tick);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (world.isClient)
        {
            return ActionResult.SUCCESS;
        }
        else if (player.isSpectator())
        {
            return ActionResult.CONSUME;
        }
        else
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomShulkerBoxBlockEntity)
            {
                CustomShulkerBoxBlockEntity shulkerBoxBlockEntity = (CustomShulkerBoxBlockEntity)blockEntity;
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
    }

    private static boolean canOpen(BlockState state, World world, BlockPos pos, CustomShulkerBoxBlockEntity entity)
    {
        if (entity.getAnimationStage() != CustomShulkerBoxBlockEntity.AnimationStage.CLOSED)
        {
            return true;
        }
        else
        {
            Box box = ShulkerEntity.calculateBoundingBox((Direction)state.get(FACING), 0.0F, 0.5F).offset(pos).contract(1.0E-6);
            return world.isSpaceEmpty(box);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getSide());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(new Property[]{FACING});
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            if (!world.isClient && player.isCreative() && !shulkerBoxBlockEntity.isEmpty())
            {
                ItemStack itemStack = getItemStack(this.getColor());
                blockEntity.setStackNbt(itemStack);
                if (shulkerBoxBlockEntity.hasCustomName())
                {
                    itemStack.setCustomName(shulkerBoxBlockEntity.getCustomName());
                }

                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
            else
            {
                shulkerBoxBlockEntity.checkLootInteraction(player);
            }
        }

        super.onBreak(world, pos, state, player);
    }

    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder)
    {
        BlockEntity blockEntity = (BlockEntity)builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            builder = builder.addDynamicDrop(CONTENTS_DYNAMIC_DROP_ID, (lootConsumer) ->
            {
                for(int i = 0; i < shulkerBoxBlockEntity.size(); ++i)
                {
                    lootConsumer.accept(shulkerBoxBlockEntity.getStack(i));
                }

            });
        }

        return super.getDroppedStacks(state, builder);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        if (itemStack.hasCustomName())
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomShulkerBoxBlockEntity)
            {
                ((CustomShulkerBoxBlockEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
    {
        if (!state.isOf(newState.getBlock()))
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomShulkerBoxBlockEntity)
            {
                world.updateComparators(pos, state.getBlock());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options)
    {
        super.appendTooltip(stack, world, tooltip, options);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null)
        {
            if (nbtCompound.contains("LootTable", 8))
            {
                tooltip.add(Text.literal("???????"));
            }

            if (nbtCompound.contains("Items", 9))
            {
                DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.readNbt(nbtCompound, defaultedList);
                int i = 0;
                int j = 0;
                Iterator var9 = defaultedList.iterator();

                while(var9.hasNext())
                {
                    ItemStack itemStack = (ItemStack)var9.next();
                    if (!itemStack.isEmpty())
                    {
                        ++j;
                        if (i <= 4)
                        {
                            ++i;
                            MutableText mutableText = itemStack.getName().copy();
                            mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
                            tooltip.add(mutableText);
                        }
                    }
                }

                if (j - i > 0)
                {
                    tooltip.add(Text.translatable("container.shulkerBox.more", new Object[]{j - i}).formatted(Formatting.ITALIC));
                }
            }
        }

    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity)
        {
            if (!shulkerBoxBlockEntity.suffocates())
            {
                return (VoxelShape)SIDES_SHAPES.get(((Direction)state.get(FACING)).getOpposite());
            }
        }

        return VoxelShapes.fullCube();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof CustomShulkerBoxBlockEntity ? VoxelShapes.cuboid(((CustomShulkerBoxBlockEntity)blockEntity).getBoundingBox(state)) : VoxelShapes.fullCube();
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        return ScreenHandler.calculateComparatorOutput((Inventory)world.getBlockEntity(pos));
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
    {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, BlockEntityType.SHULKER_BOX).ifPresent((blockEntity) ->
        {
            blockEntity.setStackNbt(itemStack);
        });
        return itemStack;
    }

    @Nullable
    public static ModDyeColor getColor(Item item) {
        return getColor(Block.getBlockFromItem(item));
    }

    @Nullable
    public static ModDyeColor getColor(Block block)
    {
        return block instanceof net.minecraft.block.ShulkerBoxBlock ? ((CustomShulkerBoxBlock)block).getColor() : null;
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
                case BEIGE -> ModBlocks.SHULKER_BOXES.get("beige");
                case BLUE_GRAY -> ModBlocks.SHULKER_BOXES.get("blue_gray");
                case CORAL -> ModBlocks.SHULKER_BOXES.get("coral");
                case EMERALD_GREEN -> ModBlocks.SHULKER_BOXES.get("emerald_green");
                case INDIGO -> ModBlocks.SHULKER_BOXES.get("indigo");
                case COGNAC -> ModBlocks.SHULKER_BOXES.get("cognac");
                case EBONY -> ModBlocks.SHULKER_BOXES.get("ebony");
                case OLIVE -> ModBlocks.SHULKER_BOXES.get("olive");
                case MINT -> ModBlocks.SHULKER_BOXES.get("mint");
                case TEAL_GREEN -> ModBlocks.SHULKER_BOXES.get("teal_green");
                case BURGUNDY -> ModBlocks.SHULKER_BOXES.get("burgundy");
                case MARSALA -> ModBlocks.SHULKER_BOXES.get("marsala");
                case FUCHSIA -> ModBlocks.SHULKER_BOXES.get("fuchsia");
                case BLUE_IRIS -> ModBlocks.SHULKER_BOXES.get("blue_iris");
                case KHAKI -> ModBlocks.SHULKER_BOXES.get("khaki");
                case AQUAMARINE -> ModBlocks.SHULKER_BOXES.get("aquamarine");
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

    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    static
    {
        FACING = FacingBlock.FACING;
        CONTENTS_DYNAMIC_DROP_ID = new Identifier("contents");
    }
}
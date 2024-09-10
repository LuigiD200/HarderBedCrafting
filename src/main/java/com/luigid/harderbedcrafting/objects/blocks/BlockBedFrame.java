package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedFrame extends BlockHorizontal {

    public static final PropertyEnum<BlockPart> PART = PropertyEnum.create("part", BlockPart.class);
    private static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125, 1.0D);

    public BlockBedFrame(String name, Material material) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        BlockInit.BLOCKS.add(this);
        setHardness(0.2F);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, BlockPart.FOOT)
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem.getItem() != ItemInit.BED_MATTRESS) return false;

        IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS.getDefaultState()
                .withProperty(FACING, state.getValue(FACING))
                .withProperty(PART, state.getValue(PART));

        worldIn.setBlockState(pos, newBlockState, 3);

        if (!playerIn.isCreative()) {
            heldItem.shrink(1);
        }

        worldIn.playSound(null, pos, SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos otherPos = pos.offset(state.getValue(PART) == BlockPart.FOOT ? facing : facing.getOpposite());
        IBlockState otherState = worldIn.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();

        if (otherBlock != this) {
            if (otherBlock == BlockInit.BED_FRAME_MATTRESS) {
                IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS.getDefaultState()
                        .withProperty(FACING, facing)
                        .withProperty(PART, state.getValue(PART) == BlockPart.FOOT ? BlockPart.FOOT : BlockPart.HEAD);
                worldIn.setBlockState(pos, newBlockState, 3);
            } else {
                if (state.getValue(PART) == BlockPart.HEAD && !worldIn.isRemote) {
                    spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_FRAME));
                }
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    protected Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(PART) == BlockPart.HEAD) {
            drops.clear();
            drops.add(new ItemStack(ItemInit.BED_FRAME));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FRAME_AABB;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ItemInit.BED_FRAME);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockPart.FOOT) {
            BlockPos blockPos = pos.offset(state.getValue(FACING));
            if (worldIn.getBlockState(blockPos).getBlock() == this) {
                worldIn.setBlockToAir(blockPos);
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 7);
        BlockPart part = (meta & 8) != 0 ? BlockPart.HEAD : BlockPart.FOOT;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(PART, part);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        // No additional state changes based on surrounding blocks, returning state as is
        return state;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(PART) == BlockPart.HEAD) {
            meta |= 8;
        }
        return meta;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, PART);
    }

    public enum BlockPart implements IStringSerializable {
        HEAD("head"),
        FOOT("foot");

        private final String name;

        BlockPart(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}

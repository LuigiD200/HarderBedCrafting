package com.luigid.harderbedcrafting.objects.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.List;

public class BlockCustomModel extends BlockBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    // Define multiple AxisAlignedBB instances representing different parts of the shape for each direction
    public static final AxisAlignedBB NS_PART1 = new AxisAlignedBB(0.25D, 0.0D, 0.375D, 0.75D, 0.5D, 0.625D);
    public static final AxisAlignedBB NS_PART2 = new AxisAlignedBB(0.5625D, 0.5D, 0.4375D, 0.6875D, 0.6875D, 0.5625D);
    public static final AxisAlignedBB NS_PART3 = new AxisAlignedBB(0.3125D, 0.5D, 0.4375D, 0.4375D, 0.6875D, 0.5625D);

    public static final AxisAlignedBB EW_PART1 = new AxisAlignedBB(0.375D, 0.0D, 0.25D, 0.625D, 0.5D, 0.75D);
    public static final AxisAlignedBB EW_PART2 = new AxisAlignedBB(0.4375D, 0.5D, 0.5625D, 0.5625D, 0.6875D, 0.6875D);
    public static final AxisAlignedBB EW_PART3 = new AxisAlignedBB(0.4375D, 0.5D, 0.3125D, 0.5625D, 0.6875D, 0.4375D);


    public BlockCustomModel(String name, Material material) {
        super(name, material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) ? NS_PART1 : EW_PART1;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);

        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NS_PART1);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NS_PART2);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NS_PART3);
        } else {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EW_PART1);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EW_PART2);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EW_PART3);
        }
    }
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);

        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
            // Combine all parts for North/South direction
            return NS_PART1;
        } else {
            // Combine all parts for East/West direction
            return EW_PART1;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}

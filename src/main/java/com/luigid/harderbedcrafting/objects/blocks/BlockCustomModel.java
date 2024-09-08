package com.luigid.harderbedcrafting.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCustomModel extends BlockBase {
    public static final AxisAlignedBB PART1 = new AxisAlignedBB(0.25D, 0.0D, 0.375D, 0.75D, 0.5D, 0.625D);
    public static final AxisAlignedBB PART2 = new AxisAlignedBB(0.5625D, 0.5D, 0.4375D, 0.6875D, 0.6875D, 0.5625D);
    public static final AxisAlignedBB PART3 = new AxisAlignedBB(0.3125D, 0.5D, 0.4375D, 0.4375D, 0.6875D, 0.5625D);

    public static final AxisAlignedBB COMBINED_BOUNDING_BOX = PART1.union(PART2).union(PART3);

    public BlockCustomModel(String name, Material material) {
        super(name, material);
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
        return COMBINED_BOUNDING_BOX; // Return the combined bounding box for rendering
    }
}

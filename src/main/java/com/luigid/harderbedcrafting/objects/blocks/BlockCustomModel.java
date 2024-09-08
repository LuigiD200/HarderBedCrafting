package com.luigid.harderbedcrafting.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCustomModel extends BlockBase {
    public static final AxisAlignedBB CUSTOM_MODEL_AB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public BlockCustomModel(String name, Material material) {
        super(name, material);
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CUSTOM_MODEL_AB;
    }
}

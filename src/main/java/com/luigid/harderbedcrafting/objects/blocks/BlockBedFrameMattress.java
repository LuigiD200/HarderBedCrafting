package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockBedFrameMattress extends BlockBedFrame {
    protected static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625, 1.0D);

    public BlockBedFrameMattress(String name, Material material) {
        super(name, material);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if (state.getValue(PART) == BlockPart.FOOT)
        {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
        }
        else if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this)
        {
            if (!worldIn.isRemote)
            {
                spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_FRAME));
                spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_MATTRESS));
            }

            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FRAME_AABB;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, world, pos, state, fortune);

        if (state.getValue(PART) == BlockPart.HEAD) {
            drops.clear();
            drops.add(new ItemStack(ItemInit.BED_FRAME));
            drops.add(new ItemStack(ItemInit.BED_MATTRESS));
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Items.AIR);
    }
}
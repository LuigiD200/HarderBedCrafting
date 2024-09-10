package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedFrameMattressPillow extends BlockBedFrameMattress {
    public BlockBedFrameMattressPillow(String name, Material material) {
        super(name, material);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }



    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos otherPos = pos.offset(state.getValue(PART) == BlockPart.FOOT ? facing : facing.getOpposite());
        IBlockState otherState = worldIn.getBlockState(otherPos);

        if (otherState.getBlock() != this) {
            if (state.getValue(PART) == BlockPart.HEAD && !worldIn.isRemote) {
                spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_FRAME));
            }
            worldIn.setBlockToAir(pos);
        }
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(PART) == BlockPart.HEAD) {
            drops.clear();
            drops.add(new ItemStack(ItemInit.BED_FRAME));
            drops.add(new ItemStack(ItemInit.BED_MATTRESS));
            drops.add(new ItemStack(ItemInit.BED_PILLOW));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }


}

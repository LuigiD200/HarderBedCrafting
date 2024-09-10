package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedFrameMattress extends BlockBedFrame {
    private static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625, 1.0D);

    public BlockBedFrameMattress(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn) {
        if (entityIn.isSneaking()) {
            super.onLanded(worldIn, entityIn);
        } else if (entityIn.motionY < 0.0D) {
            entityIn.motionY = -entityIn.motionY * 0.66D;
            if (!(entityIn instanceof EntityLivingBase)) {
                entityIn.motionY *= 0.8D;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem.getItem() != ItemInit.BED_PILLOW) return false;

        IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS_PILLOW.getDefaultState()
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
            if (otherBlock == BlockInit.BED_FRAME_MATTRESS_PILLOW) {
                IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS_PILLOW.getDefaultState()
                        .withProperty(FACING, facing)
                        .withProperty(PART, state.getValue(PART) == BlockPart.FOOT ? BlockPart.FOOT : BlockPart.HEAD);
                worldIn.setBlockState(pos, newBlockState, 3);
            } else {
                if (state.getValue(PART) == BlockPart.HEAD && !worldIn.isRemote) {
                    spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_FRAME));
                    spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_MATTRESS));
                }
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FRAME_AABB;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(PART) == BlockPart.HEAD) {
            drops.clear();
            drops.add(new ItemStack(ItemInit.BED_FRAME));
            drops.add(new ItemStack(ItemInit.BED_MATTRESS));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }
}

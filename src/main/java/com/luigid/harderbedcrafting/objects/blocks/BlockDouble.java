package com.luigid.harderbedcrafting.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDouble extends BlockBase {
    public static final PropertyEnum<BlockPart> PART = PropertyEnum.create("part", BlockPart.class);

    public BlockDouble(String name, Material material, boolean hasItemBlock) {
        super(name, material, hasItemBlock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockPart.FOOT));
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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PART);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(PART, BlockPart.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PART).ordinal();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(PART, BlockPart.FOOT);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!world.isRemote) {
            BlockPos headPos = pos.up();
            if (state.getValue(PART) == BlockPart.FOOT) {
                if (world.isAirBlock(headPos)) {
                    world.setBlockState(headPos, this.getDefaultState().withProperty(PART, BlockPart.HEAD), 2);
                } else {
                    world.setBlockToAir(pos);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && world.isAirBlock(pos.up());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            BlockPart part = state.getValue(PART);

            if (part == BlockPart.FOOT) {
                worldIn.setBlockToAir(pos.up());
            } else if (part == BlockPart.HEAD) {
                worldIn.setBlockToAir(pos.down());
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}

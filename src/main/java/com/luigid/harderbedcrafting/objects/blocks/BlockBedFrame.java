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


public class BlockBedFrame extends BlockHorizontal {

    public static final PropertyEnum<BlockPart> PART = PropertyEnum.create("part", BlockPart.class);
    protected static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125, 1.0D);

    public BlockBedFrame(String name, Material material) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);

        BlockInit.BLOCKS.add(this);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, BlockPart.FOOT)
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
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
            }

            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, world, pos, state, fortune);

        if (state.getValue(PART) == BlockPart.HEAD) {
            drops.clear();
            drops.add(new ItemStack(ItemInit.BED_FRAME));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FRAME_AABB;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }


    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ItemInit.BED_FRAME);
    }




    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockPart.FOOT)
        {
            BlockPos blockpos = pos.offset((EnumFacing)state.getValue(FACING));

            if (worldIn.getBlockState(blockpos).getBlock() == this)
            {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta & 7);
        BlockPart part = (meta & 8) != 0 ? BlockPart.HEAD : BlockPart.FOOT;
        return this.getDefaultState()
                .withProperty(FACING, enumfacing)
                .withProperty(PART, part);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(PART) == BlockPart.FOOT) {
            IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue(FACING)));
        }
        return state;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        if (state.getValue(PART) == BlockPart.HEAD) {
            i |= 8;
        }
        return i;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

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

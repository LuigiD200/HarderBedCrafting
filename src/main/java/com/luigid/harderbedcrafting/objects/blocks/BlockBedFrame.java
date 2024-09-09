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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem.getItem() == ItemInit.BED_MATTRESS) {
            IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING))
                    .withProperty(PART, state.getValue(PART));

            worldIn.setBlockState(pos, newBlockState, 3);

            if (!playerIn.isCreative()) {
                heldItem.shrink(1);
            }

            //Play sound event
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos headPos = pos.offset(facing);
        BlockPos footPos = pos.offset(facing.getOpposite());
        Block headBlock = worldIn.getBlockState(headPos).getBlock();
        Block footBlock = worldIn.getBlockState(footPos).getBlock();

        if (state.getValue(PART) == BlockPart.FOOT) {
            // Check if the HEAD block is missing
            if (headBlock != this) {
                if (headBlock == BlockInit.BED_FRAME_MATTRESS) {
                    // Update the HEAD block to match the FOOT block
                    IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS.getDefaultState()
                            .withProperty(FACING, facing)
                            .withProperty(PART, BlockPart.FOOT);
                    worldIn.setBlockState(pos, newBlockState, 3);
                } else {
                    // Remove the FOOT block if HEAD block is missing
                    worldIn.setBlockToAir(pos);
                }
            }
        } else if (state.getValue(PART) == BlockPart.HEAD) {
            // Check if the FOOT block is missing
            if (footBlock != this) {
                if (footBlock == BlockInit.BED_FRAME_MATTRESS) {
                    // Update the FOOT block to match the HEAD block
                    IBlockState newBlockState = BlockInit.BED_FRAME_MATTRESS.getDefaultState()
                            .withProperty(FACING, facing)
                            .withProperty(PART, BlockPart.HEAD);
                    worldIn.setBlockState(pos, newBlockState, 3);
                } else {
                    // Drop the item if FOOT block is missing
                    if (!worldIn.isRemote) {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemInit.BED_FRAME));
                    }
                    worldIn.setBlockToAir(pos);
                }
            }
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

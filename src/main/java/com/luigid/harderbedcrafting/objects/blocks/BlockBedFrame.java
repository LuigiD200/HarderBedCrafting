package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.HarderBedCrafting;
import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.IHasModel;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class BlockBedFrame extends BlockHorizontal implements IHasModel {

    public static final PropertyEnum<BlockPart> PART = PropertyEnum.create("part", BlockPart.class);
    protected static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockBedFrame(String name, Material material, boolean hasItemBlock) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);

        BlockInit.BLOCKS.add(this);
        if (hasItemBlock) {
            ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
        }

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, BlockPart.FOOT)
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void registerModels() {
        HarderBedCrafting.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FRAME_AABB;
    }

    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
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
    public int getMetaFromState(IBlockState state) {
        int i = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        if (state.getValue(PART) == BlockPart.HEAD) {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, PART);
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.SOLID;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer, EnumHand hand) {
        EnumFacing playerFacing = placer.getHorizontalFacing();
        return this.getDefaultState()
                .withProperty(FACING, playerFacing)
                .withProperty(PART, BlockPart.FOOT);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!world.isRemote) {
            EnumFacing facing = (EnumFacing) state.getValue(FACING);
            BlockPos headPos = pos.offset(facing);
            if (state.getValue(PART) == BlockPart.FOOT) {
                if (canPlaceBlockAt(world, headPos)) {
                        // Place the HEAD part of the block
                        world.setBlockState(headPos, this.getDefaultState()
                                .withProperty(PART, BlockPart.HEAD)
                                .withProperty(FACING, facing), 2);
                } else {
                    world.destroyBlock(pos, true);
                }
            }
        }
    }



    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        if (state.getValue(PART) == BlockPart.FOOT) {
            BlockPos headPos = pos.offset((EnumFacing)state.getValue(FACING));
            if (worldIn.getBlockState(headPos).getBlock() == this) {
                worldIn.setBlockToAir(headPos);
            }
        } else {
            BlockPos footPos = pos.offset((EnumFacing)state.getValue(FACING).getOpposite());
            if (worldIn.getBlockState(footPos).getBlock() == this) {
                worldIn.setBlockToAir(footPos);
            }
        }
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

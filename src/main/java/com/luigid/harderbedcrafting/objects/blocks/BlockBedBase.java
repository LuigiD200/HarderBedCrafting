package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.objects.BlockAndItemLink;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedBase extends BlockHorizontal {
    public static final PropertyEnum<BlockBed.EnumPartType> PART =
            PropertyEnum.create("part", BlockBed.EnumPartType.class);
    private final AxisAlignedBB FRAME_AABB;
    private final Boolean isBouncy;
    private final SoundType soundWhenPlaced;


    public BlockBedBase(String name, Material material, Boolean isBouncy, AxisAlignedBB frame) {
        super(material);
        BlockInit.registerBlock(this, name);

        blockSoundType = SoundType.WOOD;

        if (material == Material.CLOTH) soundWhenPlaced = SoundType.CLOTH;
        else soundWhenPlaced = blockSoundType;

        setHardness(0.2F);
        setDefaultState(this.blockState.getBlock().getDefaultState()
                .withProperty(PART, BlockBed.EnumPartType.FOOT)
                .withProperty(FACING, EnumFacing.NORTH));

        this.isBouncy = isBouncy;
        FRAME_AABB = frame;
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
        return FRAME_AABB;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }


    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(BlockAndItemLink.getItemForBlock(this));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
            BlockPos blockpos = pos.offset(state.getValue(FACING));
            if (worldIn.getBlockState(blockpos).getBlock() == this) {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 7);
        BlockBed.EnumPartType part = (meta & 8) != 0 ? BlockBed.EnumPartType.HEAD : BlockBed.EnumPartType.FOOT;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(PART, part);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
            meta |= 8;
        }
        return meta;
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }



    ////////////////////////////////////////
    //REAL STUFF BEGINS HERE//

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {

        ItemStack heldItem = playerIn.getHeldItem(hand);

        Block nextBlock = BlockAndItemLink.getNextBlock(this);
        if (nextBlock == null) return false;

        Item expectedItem = BlockAndItemLink.getItemForBlock(nextBlock);
        if (heldItem.getItem() != expectedItem) return false;

        IBlockState newBlockState = nextBlock.getDefaultState()
                .withProperty(FACING, state.getValue(FACING))
                .withProperty(PART, state.getValue(PART));

        worldIn.setBlockState(pos, newBlockState, 3);

        if (!playerIn.isCreative()) heldItem.shrink(1);

        worldIn.playSound(null, pos, ((BlockBedBase)nextBlock).soundWhenPlaced.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        return true;
    }


    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);

        BlockPos otherPos = pos.offset(state.getValue(PART) == BlockBed.EnumPartType.FOOT ? facing : facing.getOpposite());
        IBlockState otherState = worldIn.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();

        Block nextBlock = BlockAndItemLink.getNextBlock(this);

        if (otherBlock == this) return;

        if (nextBlock != null && otherBlock == nextBlock) {
            IBlockState newBlockState = nextBlock.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING))
                    .withProperty(PART, state.getValue(PART));

            worldIn.setBlockState(pos, newBlockState, 3);
            return;
        }

        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && !worldIn.isRemote) {
            Block currentBlock = this;
            do {
                Item itemForBlock = BlockAndItemLink.getItemForBlock(currentBlock);
                ItemStack itemStack = new ItemStack(itemForBlock);
                spawnAsEntity(worldIn, pos, itemStack);

                currentBlock = BlockAndItemLink.getPreviousBlock(currentBlock);
            } while (currentBlock != null);
        }

        worldIn.setBlockToAir(pos);
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
            super.getDrops(drops, world, pos, state, fortune);
            return;
        }

        Block currentBlock = this;
        do {
            Item itemForBlock = BlockAndItemLink.getItemForBlock(currentBlock);
            ItemStack itemStack = new ItemStack(itemForBlock);
            drops.add(itemStack);
            currentBlock = BlockAndItemLink.getPreviousBlock(currentBlock);
        } while (currentBlock != null);
    }



    /////////////////////////////////////
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (isBouncy) super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
        else super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn) {
        if (entityIn.isSneaking() || !isBouncy) super.onLanded(worldIn, entityIn);

        else if (entityIn.motionY < 0.0D) {
            entityIn.motionY = -entityIn.motionY * 0.66D;
            if (!(entityIn instanceof EntityLivingBase)) {
                entityIn.motionY *= 0.8D;
            }
        }
    }


    /////////////////////////////////////
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, PART);
    }
}

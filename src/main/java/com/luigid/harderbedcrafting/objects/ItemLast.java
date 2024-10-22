package com.luigid.harderbedcrafting.objects;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLast extends ItemBase {
    public ItemLast(String name, CreativeTabs tab, int itemStackLimit) {
        super(name, tab, itemStackLimit);
        setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos thisBlockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //BEGINNING CONDITIONS
        if (worldIn.isRemote) return EnumActionResult.SUCCESS;

        IBlockState thisBlockState = worldIn.getBlockState(thisBlockPos);
        if (thisBlockState.getBlock() != BlockAndItemLink.getLastBlock()) return EnumActionResult.FAIL;

        //THIS BLOCK INFO
        EnumFacing blockFacing = thisBlockState.getValue(BlockBedBase.FACING);
        BlockBed.EnumPartType thisBlockPart = thisBlockState.getValue(BlockBedBase.PART);

        //OTHER BLOCK INFO
        BlockPos otherBlockPos = thisBlockPos.offset(thisBlockPart == BlockBed.EnumPartType.FOOT ? blockFacing : blockFacing.getOpposite());
        IBlockState otherBlockState = worldIn.getBlockState(otherBlockPos);
        BlockBed.EnumPartType otherBlockPart = otherBlockState.getValue(BlockBedBase.PART);

        //ITEMSTACK
        ItemStack itemStack = player.getHeldItem(hand);

        //IF-STATEMENTS SECTION
        if (!player.canPlayerEdit(thisBlockPos, facing, itemStack)
                || !player.canPlayerEdit(otherBlockPos, facing, itemStack)) {
            return EnumActionResult.FAIL;
        }

        if (!worldIn.getBlockState(thisBlockPos.down()).isTopSolid()
                || !worldIn.getBlockState(otherBlockPos.down()).isTopSolid()) {
            return EnumActionResult.FAIL;
        }
        //END OF THE IF-STATEMENTS SECTION

        //SET NEW BLOCKSTATES
        IBlockState thisNewBedState = Blocks.BED.getDefaultState()
                .withProperty(BlockBed.OCCUPIED, false)
                .withProperty(BlockBed.PART, thisBlockPart)
                .withProperty(BlockBed.FACING, blockFacing);

        IBlockState otherNewBedState = thisNewBedState
                .withProperty(BlockBed.PART, otherBlockPart);

        worldIn.setBlockState(thisBlockPos, thisNewBedState, 10);
        worldIn.setBlockState(otherBlockPos, otherNewBedState, 10);

        //PLAY SOUND
        worldIn.playSound(null, thisBlockPos, Blocks.BED.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);

        //SET TILE ENTITY VALUES
        TileEntity thisTileEntity = worldIn.getTileEntity(thisBlockPos);
        if (thisTileEntity instanceof TileEntityBed) ((TileEntityBed)thisTileEntity).setItemValues(itemStack);

        TileEntity otherTileEntity = worldIn.getTileEntity(otherBlockPos);
        if (otherTileEntity instanceof TileEntityBed) ((TileEntityBed)otherTileEntity).setItemValues(itemStack);



        //TRIGGER PLACEMENT CRITERIA
        if (player instanceof EntityPlayerMP) {
            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, thisBlockPos, itemStack);
        }

        //REMOVE ITEM
        itemStack.shrink(1);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }

    public String getRegistryNameAndDyeColor (int meta) {
        return super.getRegistryName()+"_"+EnumDyeColor.byMetadata(meta).getDyeColorName();
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) for (int i = 0; i < 16; ++i)
            items.add(new ItemStack(this, 1, i));
    }
}

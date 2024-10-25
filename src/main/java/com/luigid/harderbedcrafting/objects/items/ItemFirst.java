package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.objects.BlockAndItemLink;
import com.luigid.harderbedcrafting.objects.blocks.BlockBedBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemFirst extends ItemBase {
    public ItemFirst(String name, CreativeTabs tab, int itemStackLimit) {
        super(name, tab, itemStackLimit);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos thisBlockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //BEGINNING CONDITIONS
        if (worldIn.isRemote) return EnumActionResult.SUCCESS;
        if (facing != EnumFacing.UP) return EnumActionResult.FAIL;

        //THIS BLOCK INFO
        IBlockState thisBlockState = worldIn.getBlockState(thisBlockPos);
        Block thisBlock = thisBlockState.getBlock();
        boolean isThisBlockReplaceable = thisBlock.isReplaceable(worldIn, thisBlockPos);

        if (!isThisBlockReplaceable) thisBlockPos = thisBlockPos.up();

        //OTHER BLOCK INFO
        int i = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing blockFacing = EnumFacing.getHorizontal(i);
        BlockPos otherBlockPos = thisBlockPos.offset(blockFacing);

        //ITEMSTACK
        ItemStack itemStack = player.getHeldItem(hand);

        //MASSIVE IF-STATEMENTS SECTION
        if (!player.canPlayerEdit(thisBlockPos, facing, itemStack)
                || !player.canPlayerEdit(otherBlockPos, facing, itemStack))
            return EnumActionResult.FAIL;

        IBlockState otherBlockState = worldIn.getBlockState(otherBlockPos);
        boolean isOtherBlockReplaceable = otherBlockState.getBlock().isReplaceable(worldIn, otherBlockPos);
        boolean canPlaceAtThisBlock = isThisBlockReplaceable || worldIn.isAirBlock(thisBlockPos);
        boolean canPlaceAtOtherBlock = isOtherBlockReplaceable || worldIn.isAirBlock(otherBlockPos);

        if (!canPlaceAtThisBlock || !canPlaceAtOtherBlock
                || !worldIn.getBlockState(thisBlockPos.down()).isTopSolid()
                || !worldIn.getBlockState(otherBlockPos.down()).isTopSolid())
            return EnumActionResult.FAIL;
        //END OF THE MASSIVE IF-STATEMENTS SECTION

        //SET NEW BLOCKSTATES
        IBlockState thisNewBlockState = BlockAndItemLink.getFirstBlock().getDefaultState()
                .withProperty(BlockBedBase.PART, BlockBed.EnumPartType.FOOT)
                .withProperty(BlockBedBase.FACING, blockFacing);

        IBlockState otherNewBlockState = thisNewBlockState
                .withProperty(BlockBedBase.PART, BlockBed.EnumPartType.HEAD);

        worldIn.setBlockState(thisBlockPos, thisNewBlockState, 10);
        worldIn.setBlockState(otherBlockPos, otherNewBlockState , 10);

        //PLAY SOUND
        SoundType soundtype = thisNewBlockState.getBlock().getSoundType(thisNewBlockState, worldIn, thisBlockPos, player);
        worldIn.playSound((EntityPlayer)null, thisBlockPos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

        //REMOVE ITEM
        itemStack.shrink(1);
        return EnumActionResult.SUCCESS;

    }
}


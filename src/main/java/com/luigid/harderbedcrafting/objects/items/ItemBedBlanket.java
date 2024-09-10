package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrame;
import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrameMattressPillow;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBedBlanket extends Item {
    public ItemBedBlanket(String name) {
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setMaxDamage(0);
        setHasSubtypes(true);

        ItemInit.ITEMS.add(this);
    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getBlock() != BlockInit.BED_FRAME_MATTRESS_PILLOW) return EnumActionResult.FAIL;
        if (worldIn.isRemote) return EnumActionResult.SUCCESS;

        //MAIN BLOCK
        IBlockState mainBlockState = worldIn.getBlockState(pos);
        Block mainBlock = mainBlockState.getBlock();
        BlockBedFrameMattressPillow.BlockPart mainBlockPart = mainBlockState.getValue(BlockBedFrameMattressPillow.PART);

        //FACING
        EnumFacing enumfacing = mainBlockState.getValue(BlockBedFrameMattressPillow.FACING);

        //OTHER BLOCK
        BlockPos otherPos = pos.offset(mainBlockPart == BlockBedFrame.BlockPart.FOOT ? enumfacing : enumfacing.getOpposite());
        IBlockState otherBlockState = worldIn.getBlockState(otherPos);
        Block otherBlock = otherBlockState.getBlock();
        BlockBedFrameMattressPillow.BlockPart otherBlockPart = otherBlockState.getValue(BlockBedFrameMattressPillow.PART);


        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(otherPos, facing, itemstack)) {
            IBlockState newMainBlockState = Blocks.BED.getDefaultState()
                    .withProperty(BlockBed.OCCUPIED, false)
                    .withProperty(BlockBed.FACING, enumfacing)
                    .withProperty(BlockBed.PART, getNewBlockPart(mainBlockPart));

            IBlockState newOtherBlockState = Blocks.BED.getDefaultState()
                    .withProperty(BlockBed.OCCUPIED, false)
                    .withProperty(BlockBed.FACING, enumfacing)
                    .withProperty(BlockBed.PART, getNewBlockPart(otherBlockPart));

            worldIn.setBlockState(pos, newMainBlockState, 10);
            worldIn.setBlockState(otherPos, newOtherBlockState, 10);

            worldIn.playSound(null, pos, SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

            TileEntity tileentity = worldIn.getTileEntity(otherPos);
            if (tileentity instanceof TileEntityBed) ((TileEntityBed)tileentity).setItemValues(itemstack);

            TileEntity tileentity1 = worldIn.getTileEntity(pos);
            if (tileentity1 instanceof TileEntityBed) ((TileEntityBed)tileentity1).setItemValues(itemstack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else return EnumActionResult.FAIL;
    }

    private BlockBed.EnumPartType getNewBlockPart(BlockBedFrameMattressPillow.BlockPart oldPart) {
        return oldPart.getName().equals("head") ? BlockBed.EnumPartType.HEAD : BlockBed.EnumPartType.FOOT;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }

    public String getRegistryNameAndDyeColor (int meta) {
        return super.getRegistryName()+"_"+EnumDyeColor.byMetadata(meta).getDyeColorName();
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 16; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}

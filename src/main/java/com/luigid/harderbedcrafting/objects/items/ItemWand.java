package com.luigid.harderbedcrafting.objects.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemWand extends ItemBase {
    private Block changeToBlock = null;
    public ItemWand(String name, Block changeToBlock) {
        super(name);
        this.changeToBlock = changeToBlock;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            //THIS DOESN'T WORK IF changeToBlock IS A NON-VANILLA BLOCK SINCE ITEMS ARE REGISTERED BEFORE BLOCKS
            world.setBlockState(pos, changeToBlock.getDefaultState());
        }

        return EnumActionResult.SUCCESS;
    }
}

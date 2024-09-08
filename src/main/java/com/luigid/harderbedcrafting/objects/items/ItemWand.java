package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.init.BlockInit;
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
    public ItemWand(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            world.setBlockState(pos, BlockInit.EXAMPLE_BLOCK_WITH_NO_ITEMBLOCK.getDefaultState());
        }

        return EnumActionResult.SUCCESS;
    }
}

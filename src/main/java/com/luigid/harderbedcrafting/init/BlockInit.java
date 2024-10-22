package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.BlockBedBase;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block BED_FRAME = new BlockBedBase("bed_frame", Material.WOOD, false,
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125, 1.0D));
    public static final Block BED_FRAME_MATTRESS = new BlockBedBase("bed_frame_mattress", Material.CLOTH, true,
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625, 1.0D));
    public static final Block BED_FRAME_MATTRESS_PILLOW = new BlockBedBase("bed_frame_mattress_pillow", Material.CLOTH, true,
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625, 1.0D));


    public static void registerBlock(Block block, String blockName) {
        block.setUnlocalizedName(Reference.MOD_ID + "." + blockName);
        block.setRegistryName(blockName);
        BlockInit.BLOCKS.add(block);
    }
}

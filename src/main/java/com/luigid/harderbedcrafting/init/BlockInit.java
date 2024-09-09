package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrame;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final Block BED_FRAME = new BlockBedFrame("bed_frame", Material.CLOTH);
}

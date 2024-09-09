package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrame;
import com.luigid.harderbedcrafting.objects.blocks.BlockCustomModel;
import com.luigid.harderbedcrafting.objects.blocks.BlockDouble;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final Block EXAMPLE_BLOCK_CUSTOM_MODEL = new BlockCustomModel("example_block_custom_model", Material.ROCK);
    public static final Block EXAMPLE_DOUBLE_BLOCK = new BlockDouble("example_double_block", Material.ROCK, true);
    public static final Block BED_FRAME = new BlockBedFrame("bed_frame", Material.CLOTH);
}

package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.blocks.BlockBase;
import com.luigid.harderbedcrafting.objects.blocks.BlockCustomModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block EXAMPLE_BLOCK = new BlockBase("example_block", Material.ROCK);
    public static final Block EXAMPLE_BLOCK_WITH_NO_ITEMBLOCK = new BlockBase("example_block_with_no_itemblock", Material.ROCK, false);
    public static final Block EXAMPLE_BLOCK_CUSTOM_MODEL = new BlockCustomModel("example_block_custom_model", Material.ROCK);
}

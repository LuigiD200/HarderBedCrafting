package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrame;
import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrameMattress;
import com.luigid.harderbedcrafting.objects.blocks.BlockBedFrameMattressPillow;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final Block BED_FRAME = new BlockBedFrame("bed_frame", Material.CLOTH);
    public static final Block BED_FRAME_MATTRESS = new BlockBedFrameMattress("bed_frame_mattress", Material.CLOTH);
    public static final Block BED_FRAME_MATTRESS_PILLOW = new BlockBedFrameMattressPillow("bed_frame_mattress_pillow", Material.CLOTH);
}

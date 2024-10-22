package com.luigid.harderbedcrafting.objects;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BlockAndItemLink {
    private static final Map<Block, Item> blockToItemMap = new HashMap<>();
    private static final Map<Item, Block> itemToBlockMap = new HashMap<>();
    private static final List<Block> blockSuccessionList = new ArrayList<>();

    //This is used to load the attributes
    static {
        linkBlockAndItem(BlockInit.BED_FRAME, ItemInit.BED_FRAME);

        linkBlockAndItem(BlockInit.BED_FRAME_MATTRESS, ItemInit.BED_MATTRESS);
        linkBlockAndItem(BlockInit.BED_FRAME_MATTRESS_PILLOW, ItemInit.BED_PILLOW);

        linkBlockAndItem(Blocks.BED, null);
    }

    private static void linkBlockAndItem(Block block, Item item) {
        blockToItemMap.put(block, item);
        if (item != null) {
            itemToBlockMap.put(item, block);
            blockSuccessionList.add(block);
        }
    }

    //PUBLIC METHODS
    public static Item getItemForBlock(Block block) {
        return blockToItemMap.get(block);
    }

    public static Block getBlockForItem(Item item) {
        return itemToBlockMap.get(item);
    }

    public static Block getNextBlock(Block currentBlock) {
        int currentIndex = blockSuccessionList.indexOf(currentBlock);
        if (currentIndex != -1 && currentIndex < blockSuccessionList.size() - 1) {
            return blockSuccessionList.get(currentIndex + 1);
        }
        return null;
    }

    public static Block getPreviousBlock(Block currentBlock) {
        int currentIndex = blockSuccessionList.indexOf(currentBlock);
        if (currentIndex > 0) {
            return blockSuccessionList.get(currentIndex - 1);
        }
        return null;
    }

    public static Block getFirstBlock() {
        if (blockSuccessionList.isEmpty()) return null;
        return blockSuccessionList.get(0);
    }

    public static Block getLastBlock() {
        if (blockSuccessionList.isEmpty()) return null;
        return blockSuccessionList.get(blockSuccessionList.size() - 1);
    }
}

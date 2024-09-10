package com.luigid.harderbedcrafting.util.handlers;

import com.luigid.harderbedcrafting.HarderBedCrafting;
import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        for (Item item : ItemInit.ITEMS) HarderBedCrafting.proxy.registerItemRenderer(item, 0, "inventory");

        for (int i=1; i < 16; i++) HarderBedCrafting.proxy.registerItemRenderer(ItemInit.BED_BLANKET, i, "inventory");

        for (Block block : BlockInit.BLOCKS) HarderBedCrafting.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
    }
}

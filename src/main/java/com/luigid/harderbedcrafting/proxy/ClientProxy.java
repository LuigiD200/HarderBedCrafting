package com.luigid.harderbedcrafting.proxy;

import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.objects.ItemLast;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), id);

        if (item == ItemInit.BED_BLANKET) {
            ItemLast blanket = (ItemLast) item;
            modelResourceLocation = new ModelResourceLocation(blanket.getRegistryNameAndDyeColor(meta), id);
        }

        ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
    }
}

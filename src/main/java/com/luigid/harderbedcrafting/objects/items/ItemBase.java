package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.HarderBedCrafting;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.proxy.ClientProxy;
import com.luigid.harderbedcrafting.util.IHasModel;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name) {
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        HarderBedCrafting.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

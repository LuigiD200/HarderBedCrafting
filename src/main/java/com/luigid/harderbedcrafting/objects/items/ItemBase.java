package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    private int itemStackLimit=64;

    public ItemBase(String name, CreativeTabs tab) {
        this(name, tab, 64);
    }
    public ItemBase(String name, CreativeTabs tab, int itemStackLimit) {
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(tab);

        ItemInit.ITEMS.add(this);
        this.itemStackLimit = itemStackLimit;
    }

    @Override
    public int getItemStackLimit() {
        return itemStackLimit;
    }
}

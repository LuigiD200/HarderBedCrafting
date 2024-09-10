package com.luigid.harderbedcrafting.objects.items;

import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item {
    public ItemBase(String name) {
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}

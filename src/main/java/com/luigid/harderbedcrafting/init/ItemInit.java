package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import com.luigid.harderbedcrafting.objects.items.ItemWand;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item EXAMPLE_ITEM = new ItemBase("example_item");
    public static final Item EXAMPLE_PLUS_ITEM = new ItemBase("example_plus_item");
    public static final Item EXAMPLE_ITEM_WAND = new ItemWand("example_item_wand", BlockInit.EXAMPLE_BLOCK_WITH_NO_ITEMBLOCK);

}
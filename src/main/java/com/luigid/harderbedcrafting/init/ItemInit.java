package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item EXAMPLE_ITEM = new ItemBase("example_item");
}
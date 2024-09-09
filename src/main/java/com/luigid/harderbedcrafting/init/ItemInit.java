package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import com.luigid.harderbedcrafting.objects.items.ItemBedFrame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item BED_FRAME = new ItemBedFrame("bed_frame");
    public static final Item BED_MATTRESS = new ItemBase("bed_mattress") {
        @Override
        public int getItemStackLimit(ItemStack stack) {
            return 1;
        }
    };

}
package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import com.luigid.harderbedcrafting.objects.items.ItemBedFrame;
import com.luigid.harderbedcrafting.objects.items.ItemBedMattress;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item BED_FRAME = new ItemBedFrame("bed_frame");
    public static final Item BED_MATTRESS = new ItemBedMattress("bed_mattress");

}
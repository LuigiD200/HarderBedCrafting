package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import com.luigid.harderbedcrafting.objects.items.ItemLast;
import com.luigid.harderbedcrafting.objects.items.ItemFirst;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item BED_FRAME = new ItemFirst("bed_frame", CreativeTabs.DECORATIONS, 1);
    public static final Item BED_MATTRESS = new ItemBase("bed_mattress", CreativeTabs.DECORATIONS, 1);
    public static final Item BED_PILLOW = new ItemBase("bed_pillow", CreativeTabs.DECORATIONS, 1);
    public static final Item BED_BLANKET = new ItemLast("bed_blanket", CreativeTabs.DECORATIONS, 1);


    //INGREDIENTS
    public static final Item BED_FRAME_BASE = new ItemBase("bed_frame_base", CreativeTabs.MISC);
    public static final Item BED_FRAME_LEG = new ItemBase("bed_frame_leg", CreativeTabs.MISC);
    public static final Item PADDED_WOOL = new ItemBase("padded_wool", CreativeTabs.MISC);
    public static final Item BED_FABRIC = new ItemBase("bed_fabric", CreativeTabs.MISC);
    public static final Item BED_SPRING = new ItemBase("bed_spring", CreativeTabs.MISC);
}
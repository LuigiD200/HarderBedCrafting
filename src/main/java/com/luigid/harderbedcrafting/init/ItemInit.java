package com.luigid.harderbedcrafting.init;

import com.luigid.harderbedcrafting.objects.items.ItemBase;
import com.luigid.harderbedcrafting.objects.items.ItemBedBlanket;
import com.luigid.harderbedcrafting.objects.items.ItemBedFrame;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item BED_FRAME = new ItemBedFrame("bed_frame");
    public static final Item BED_MATTRESS = new ItemBase("bed_mattress", CreativeTabs.DECORATIONS) {
        @Override
        public int getItemStackLimit(ItemStack stack) {
            return 1;
        }
    };
    public static final Item BED_PILLOW = new ItemBase("bed_pillow", CreativeTabs.DECORATIONS) {
        @Override
        public int getItemStackLimit(ItemStack stack) {
            return 1;
        }
    };
    public static final Item BED_BLANKET = new ItemBedBlanket("bed_blanket");


    //INGREDIENTS
    public static final Item BED_FRAME_BASE = new ItemBase("bed_frame_base", CreativeTabs.MISC);
    public static final Item BED_FRAME_LEG = new ItemBase("bed_frame_leg", CreativeTabs.MISC);
    public static final Item PADDED_WOOL = new ItemBase("padded_wool", CreativeTabs.MISC);
    public static final Item BED_FABRIC = new ItemBase("bed_fabric", CreativeTabs.MISC);
    public static final Item BED_SPRING = new ItemBase("bed_spring", CreativeTabs.MISC);

}
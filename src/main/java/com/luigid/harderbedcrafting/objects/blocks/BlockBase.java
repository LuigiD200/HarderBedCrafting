package com.luigid.harderbedcrafting.objects.blocks;

import com.luigid.harderbedcrafting.init.BlockInit;
import com.luigid.harderbedcrafting.init.ItemInit;
import com.luigid.harderbedcrafting.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

import java.util.Objects;

public class BlockBase extends Block {
    public BlockBase(String name, Material material, boolean hasItemBlock) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.DECORATIONS);

        BlockInit.BLOCKS.add(this);

        if (hasItemBlock) ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    //If you don't specify whenever the block has or doesn't have an ItemBlock, the code will interpret that as true
    public BlockBase(String name, Material material) {
        this(name, material, true);
    }
}

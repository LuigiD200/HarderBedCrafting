package com.luigid.harderbedcrafting;

import com.luigid.harderbedcrafting.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = HarderBedCrafting.MOD_ID, name = HarderBedCrafting.NAME, version = HarderBedCrafting.VERSION)
public class HarderBedCrafting {
    public static final String MOD_ID = "harderbedcrafting";
    public static final String NAME = "Harder Bed Crafting";
    public static final String VERSION = "1.O";
    private static final String CLIENT = "com.luigid.harderbedcrafting.proxy.ClientProxy";
    private static final String COMMON = "com.luigid.harderbedcrafting.proxy.CommonProxy";

    @Mod.Instance
    public static HarderBedCrafting instance;


    @SidedProxy(clientSide = CLIENT, serverSide = COMMON)
    public static CommonProxy proxy;


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {}



}

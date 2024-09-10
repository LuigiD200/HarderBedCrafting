package com.luigid.harderbedcrafting.util.handlers;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber
public class RecipeEventHandler {

    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
        removeRecipe(event, "white_bed");
        removeRecipe(event, "orange_bed");
        removeRecipe(event, "magenta_bed");
        removeRecipe(event, "light_blue_bed");
        removeRecipe(event, "yellow_bed");
        removeRecipe(event, "lime_bed");
        removeRecipe(event, "pink_bed");
        removeRecipe(event, "gray_bed");
        removeRecipe(event, "light_gray_bed");
        removeRecipe(event, "cyan_bed");
        removeRecipe(event, "purple_bed");
        removeRecipe(event, "blue_bed");
        removeRecipe(event, "brown_bed");
        removeRecipe(event, "green_bed");
        removeRecipe(event, "red_bed");
        removeRecipe(event, "black_bed");
    }

    private static void removeRecipe(RegistryEvent.Register<IRecipe> event, String recipeName) {
        IForgeRegistryModifiable<IRecipe> modifiableRegistry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        modifiableRegistry.remove(new ResourceLocation("minecraft:"+recipeName));
    }
}

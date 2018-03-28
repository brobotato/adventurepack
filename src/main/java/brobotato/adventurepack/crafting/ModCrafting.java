package brobotato.adventurepack.crafting;

import brobotato.adventurepack.AdventurePack;
import brobotato.adventurepack.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class ModCrafting {
    public static ModArmorDyes modArmorDyes = new ModArmorDyes();

    public static void register(IForgeRegistry<IRecipe> registry) {
        registry.registerAll(modArmorDyes.setRegistryName(new ResourceLocation(AdventurePack.modId, "explorer_hat_dyeing")));
    }
}
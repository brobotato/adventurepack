package brobotato.adventurepack;

import brobotato.adventurepack.item.ModItems;
import brobotato.adventurepack.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = AdventurePack.modId, name = AdventurePack.name, version = AdventurePack.version)

public class AdventurePack {
    public static final String modId = "adventurepack";
    public static final String name = "Adventure Pack";
    public static final String version = "1.0.0";

    @SidedProxy(serverSide = "brobotato.adventurepack.proxy.CommonProxy", clientSide = "brobotato.adventurepack.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(modId)
    public static AdventurePack instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(name + " is loading!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }
}
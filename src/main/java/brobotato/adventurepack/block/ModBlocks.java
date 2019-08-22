package brobotato.adventurepack.block;

import brobotato.adventurepack.AdventurePack;
import brobotato.adventurepack.block.tileentity.TileEntityLight;
import brobotato.adventurepack.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = AdventurePack.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AdventurePack.modId)
public final class ModBlocks {

    @ObjectHolder("block_light")
    public static Block blockLight;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> registry = evt.getRegistry();
        registry.register(new BlockLight(Block.Properties.create(Material.AIR)).setRegistryName(AdventurePack.modId, "block_light"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();
        Item.Properties props = ModItems.defaultBuilder();
        r.register(new ItemBlock(blockLight, props).setRegistryName(blockLight.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> evt) {
        IForgeRegistry<TileEntityType<?>> registry = evt.getRegistry();
        registry.register(TileEntityType.Builder.create(TileEntityLight::new).build(null).setRegistryName(AdventurePack.modId, "tile_entity_light"));
    }

}

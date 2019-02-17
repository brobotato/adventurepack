package brobotato.adventurepack.proxy;

import brobotato.adventurepack.AdventurePack;
import brobotato.adventurepack.RenderHighlightedHandler;
import brobotato.adventurepack.item.armor.ModelExplorerHat;
import brobotato.adventurepack.item.armor.ModelMiningHelm;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    private static final ModelMiningHelm m_helm = new ModelMiningHelm(1.0f);
    public static final String M_HELM = "MHELM";
    private static final ModelExplorerHat e_hat = new ModelExplorerHat(1.0f);
    public static final String E_HAT = "EHAT";

    @Override
    public ModelBiped getArmorModel(String type) {

        switch (type) {
            case M_HELM:
                return m_helm;
            case E_HAT:
                return e_hat;
            default:
                break;
        }

        return null;
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(AdventurePack.modId + ":" + id, "inventory"));
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        MinecraftForge.EVENT_BUS.register(new RenderHighlightedHandler());
    }

}

package brobotato.adventurepack.item.armor;

import brobotato.adventurepack.AdventurePack;
import brobotato.adventurepack.client.model.ModelExplorerHat;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class ItemExplorerHat extends DyeableArmorItem {

    public static final IArmorMaterial explorerArmorMaterial = new IArmorMaterial() {
        private final int[] damageReduction = {2, 2, 2, 2};

        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return 133;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return damageReduction[slotIn.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return 9;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "leather"));
            return Ingredient.fromItems(item);
        }

        @Override
        public String getName() {
            return "explorer";
        }

        @Override
        public float getToughness() {
            return 0;
        }
    };

    protected BipedModel model = new ModelExplorerHat(1.0f);

    public final EquipmentSlotType type;

    public ItemExplorerHat(EquipmentSlotType type, IArmorMaterial mat, Properties props) {
        super(mat, type, props);
        this.type = type;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel original) {
        return model;
    }

    @Nonnull
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return AdventurePack.modId + ":" + "/textures/models/armor/explorer_layer_1.png";
    }
}

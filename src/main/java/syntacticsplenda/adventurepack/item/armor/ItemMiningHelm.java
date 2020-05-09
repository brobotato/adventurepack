package syntacticsplenda.adventurepack.item.armor;

import syntacticsplenda.adventurepack.AdventurePack;
import syntacticsplenda.adventurepack.block.ModBlocks;
import syntacticsplenda.adventurepack.config.ModConfig;
import syntacticsplenda.adventurepack.proxy.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMiningHelm extends ItemArmor {

    public static final ItemArmor.ArmorMaterial miningArmorMaterial = EnumHelper.addArmorMaterial("MINING",
            AdventurePack.modId + ":mining", 15, new int[]{1, 1, 1, 1}, 9,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    public ItemMiningHelm() {
        super(miningArmorMaterial, EntityEquipmentSlot.HEAD, "mining_helmet");
    }

    private RayTraceResult rayTrace(double blockReachDistance, float partialTicks, EntityPlayer player) {
        Vec3d vec3d = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, true, true);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote) {
            if (itemStack.hasTagCompound() && itemStack.getTagCompound().getInteger("on") == 1) return;
            RayTraceResult lookPos = rayTrace(ModConfig.helmetRange, 1.0f, player);
            BlockPos pos;
            if (lookPos == null) return;
            if (lookPos.sideHit != null) pos = lookPos.getBlockPos().offset(lookPos.sideHit);
            else pos = lookPos.getBlockPos();
            double vecDistance = Math.pow(lookPos.hitVec.squareDistanceTo(player.posX, player.posY, player.posZ), 0.5);
            if (vecDistance <= ModConfig.helmetRange) {
                if (world.getBlockState(pos).getBlock().isAir(world.getBlockState(pos), world, pos)) {
                    player.world.setBlockState(pos, ModBlocks.blockLight.getDefaultState(), 2);
                } else if (world.getBlockState(pos.add(0, 1, 0)).getBlock().isAir(world.getBlockState(pos.add(0, 1, 0)), world, pos.add(0, 1, 0))) {
                    player.world.setBlockState(pos.add(0, 1, 0), ModBlocks.blockLight.getDefaultState(), 2);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!playerIn.isSneaking()) super.onItemRightClick(worldIn, playerIn, handIn);
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!itemStack.hasTagCompound()) {
            itemStack.getOrCreateSubCompound("on");
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("on", new NBTTagInt(1));
            itemStack.setTagCompound(tag);
        }
        if (itemStack.getTagCompound().getInteger("on") == 1) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("on", new NBTTagInt(0));
            itemStack.setTagCompound(tag);
        } else if (itemStack.getTagCompound().getInteger("on") == 0) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("on", new NBTTagInt(1));
            itemStack.setTagCompound(tag);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
                                    ModelBiped defaultModel) {

        if (itemStack != null) {
            if (itemStack.getItem() instanceof ItemArmor) {

                EntityEquipmentSlot type = ((ItemArmor) itemStack.getItem()).armorType;
                ModelBiped armorModel = null;
                switch (type) {
                    case HEAD:
                        armorModel = AdventurePack.proxy.getArmorModel(ClientProxy.M_HELM);
                        break;
                    case LEGS:
                    case FEET:
                    case CHEST:
                    default:
                        break;
                }

                armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
                armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
                armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
                armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
                armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
                armorModel.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
                        || (armorSlot == EntityEquipmentSlot.FEET);
                armorModel.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
                        || (armorSlot == EntityEquipmentSlot.FEET);

                armorModel.isSneak = defaultModel.isSneak;
                armorModel.isRiding = defaultModel.isRiding;
                armorModel.isChild = defaultModel.isChild;
                armorModel.rightArmPose = defaultModel.rightArmPose;
                armorModel.leftArmPose = defaultModel.leftArmPose;

                return armorModel;
            }
        }
        return null;
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        TextComponentString info = new TextComponentString("Shift-right-click to toggle");
        info.setStyle(new Style().setItalic(true));
        tooltip.add(info.getFormattedText());
    }
}

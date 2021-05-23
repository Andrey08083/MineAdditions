package ua.andrey08xtomyoll.mineadditions.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

import javax.annotation.Nonnull;

public class ChestplateBase extends ArmorBase implements IHasModel, IHasEffect {

    public boolean hasFlyMode = false;

    public ChestplateBase(String name, ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setCreativeTab(ModMain.creativeTab);
    }

    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack stack) {
        if (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getTranslationKey().equals(this.getTranslationKey()) && ConfigHandler.generalSettings.canFly) {
            player.capabilities.allowFlying = true;
            hasFlyMode = true;
        }
    }

    @Override
    public void registerModels() {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

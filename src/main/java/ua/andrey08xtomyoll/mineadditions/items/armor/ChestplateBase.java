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

/**
 * Клас-конструктор нагрудника
 */
public class ChestplateBase extends ArmorBase implements IHasModel, IHasEffect {

    public boolean isEquipped = false;

    /**
     * Конструктор класу нагрудника
     * @param name реєстраційне ім'я
     * @param materialIn матеріал, від якого наслідуються властивості
     * @param renderIndexIn індекс відмальовки
     * @param equipmentSlotIn слот, в який можна розмістити броню
     */
    public ChestplateBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setCreativeTab(ModMain.creativeTab);
    }

    /**
     * Подія, яка відбувається кожен ігровий тік, при одягнутій броні на гравця
     * @param world світ, в якмоу відбувається подія
     * @param player гравець, на якому одягнута броня
     * @param stack броня
     */
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack stack) {
        isEquipped = true;
        player.capabilities.allowFlying = ConfigHandler.generalSettings.canFly;
    }

    @Override
    public void registerModels() {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

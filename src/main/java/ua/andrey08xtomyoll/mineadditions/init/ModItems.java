package ua.andrey08xtomyoll.mineadditions.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.items.*;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.armor.ChestplateBase;
import ua.andrey08xtomyoll.mineadditions.items.records.UntitledRecord;
import ua.andrey08xtomyoll.mineadditions.items.seeds.LabatiumFruitSeed;
import ua.andrey08xtomyoll.mineadditions.items.seeds.TomiumFruitSeed;
import ua.andrey08xtomyoll.mineadditions.items.tools.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас з предметами
 */
public class ModItems {

    //Materials
    public static final Item.ToolMaterial MATERIAL_LABATIUM = EnumHelper.addToolMaterial("material_labatium", 4, 3000, 15.0F, 15.0F, 20);
    public static final ItemArmor.ArmorMaterial ARMOR_LABATIUM = EnumHelper.addArmorMaterial("armormateral_labatium", "armormaterial_labatium", ConfigHandler.labatiumSettings.overallResistance, new int[]{ConfigHandler.labatiumSettings.helmetResistance, ConfigHandler.labatiumSettings.chestplateResistance, ConfigHandler.labatiumSettings.leggingsResistance, ConfigHandler.labatiumSettings.bootsResistance}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ConfigHandler.labatiumSettings.overallToughness).setRepairItem(new ItemStack(ModItems.LABATIUM));

    public static final Item.ToolMaterial MATERIAL_TOMIUM = EnumHelper.addToolMaterial("material_tomium", 4, 3000, 15.0F, 15.0F, 20);
    public static final ItemArmor.ArmorMaterial ARMOR_TOMIUM = EnumHelper.addArmorMaterial("armormateral_tomium", "armormaterial_tomium", ConfigHandler.tomiumSettings.overallResistance, new int[]{ConfigHandler.tomiumSettings.helmetResistance, ConfigHandler.tomiumSettings.chestplateResistance, ConfigHandler.tomiumSettings.leggingsResistance, ConfigHandler.tomiumSettings.bootsResistance}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ConfigHandler.tomiumSettings.overallToughness).setRepairItem(new ItemStack(ModItems.TOMIUM));


    //Items
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item LABATIUM = new ItemGlowingBase("labatium");
    public static final Item TOMIUM = new ItemGlowingBase("tomium");
    public static final Item LITTLE_LABATIUM_DUST = new ItemBase("little_labatium_dust");
    public static final Item LITTLE_TOMIUM_DUST = new ItemBase("little_tomium_dust");
    public static final Item LABATIUM_DUST = new ItemBase("labatium_dust");
    public static final Item TOMIUM_DUST = new ItemBase("tomium_dust");

    public static final Item ITEM_VAN_SHOOT = new ItemShoot("van_shoot_low");

    //Tools
    public static final ItemPickaxe LABATIUM_PICKAXE = new ToolPickaxe("labatium_pickaxe", MATERIAL_LABATIUM);
    public static final ToolHoe LABATIUM_HOE = new ToolHoe("labatium_hoe", MATERIAL_LABATIUM);
    public static final ItemSword LABATIUM_SWORD = new ToolSword("labatium_sword", MATERIAL_LABATIUM);
    public static final ItemAxe LABATIUM_AXE = new ToolAxe("labatium_axe", MATERIAL_LABATIUM);
    public static final ItemSpade LABATIUM_SHOVEL = new ToolSpade("labatium_shovel", MATERIAL_LABATIUM);

    public static final ItemPickaxe TOMIUM_PICKAXE = new ToolPickaxe("tomium_pickaxe", MATERIAL_TOMIUM);
    public static final ToolHoe TOMIUM_HOE = new ToolHoe("tomium_hoe", MATERIAL_TOMIUM);
    public static final ItemSword TOMIUM_SWORD = new ToolSword("tomium_sword", MATERIAL_TOMIUM);
    public static final ItemAxe TOMIUM_AXE = new ToolAxe("tomium_axe", MATERIAL_TOMIUM);
    public static final ItemSpade TOMIUM_SHOVEL = new ToolSpade("tomium_shovel", MATERIAL_TOMIUM);

    //Armor
    public static final ItemArmor LABATIUM_BOOTS = new ArmorBase("labatium_boots", ARMOR_LABATIUM, 1, EntityEquipmentSlot.FEET);
    public static final ItemArmor LABATIUM_LEGGS = new ArmorBase("labatium_leggings", ARMOR_LABATIUM, 2, EntityEquipmentSlot.LEGS);
    public static final ChestplateBase LABATIUM_CHESTPLATE = new ChestplateBase("labatium_chestplate", ARMOR_LABATIUM, 1, EntityEquipmentSlot.CHEST);
    public static final ItemArmor LABATIUM_HEAD = new ArmorBase("labatium_helmet", ARMOR_LABATIUM, 1, EntityEquipmentSlot.HEAD);

    public static final ItemArmor TOMIUM_BOOTS = new ArmorBase("tomium_boots", ARMOR_TOMIUM, 1, EntityEquipmentSlot.FEET);
    public static final ItemArmor TOMIUM_LEGGINGS = new ArmorBase("tomium_leggings", ARMOR_TOMIUM, 2, EntityEquipmentSlot.LEGS);
    public static final ChestplateBase TOMIUM_CHESTPLATE = new ChestplateBase("tomium_chestplate", ARMOR_TOMIUM, 1, EntityEquipmentSlot.CHEST);
    public static final ItemArmor TOMIUM_HELMET = new ArmorBase("tomium_helmet", ARMOR_TOMIUM, 1, EntityEquipmentSlot.HEAD);

    //Seeds
    public static final Item LABATIUM_FRUIT_SEED = new LabatiumFruitSeed("labatium_fruit_seed");
    public static final Item TOMIUM_FRUIT_SEED = new TomiumFruitSeed("tomium_fruit_seed");

    //food
    public static final Item LABATIUM_FRUIT = new ItemLabatiumFruit("labatium_fruit", 1, false);
    public static final Item TOMIUM_FRUIT = new ItemTomiumFruit("tomium_fruit", 1, false);
    //Records
    public static final Item UNTITLED_RECORD = new UntitledRecord("untitled_record", ModSounds.untitledRecord);

}

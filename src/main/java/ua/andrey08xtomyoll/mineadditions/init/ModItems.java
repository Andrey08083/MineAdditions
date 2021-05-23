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
import ua.andrey08xtomyoll.mineadditions.items.seeds.MazuriumFruitSeed;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolAxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSpade;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSword;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    //Materials
    public static final Item.ToolMaterial MATERIAL_LABATIUM = EnumHelper.addToolMaterial("material_labatium", 4, 3000, 15.0F, 15.0F, 20);
    public static final ItemArmor.ArmorMaterial ARMOR_LABATIUM = EnumHelper.addArmorMaterial("armormateral_labatium", "armormaterial_labatium", ConfigHandler.labatiumSettings.overallResistance, new int[]{ConfigHandler.labatiumSettings.helmetResistance, ConfigHandler.labatiumSettings.chestplateResistance, ConfigHandler.labatiumSettings.leggingsResistance, ConfigHandler.labatiumSettings.bootsResistance}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ConfigHandler.labatiumSettings.overallToughness).setRepairItem(new ItemStack(ModItems.LABATIUM));

    public static final Item.ToolMaterial MATERIAL_MAZURIUM = EnumHelper.addToolMaterial("material_mazurium", 4, 3000, 15.0F, 15.0F, 20);
    public static final ItemArmor.ArmorMaterial ARMOR_MAZURIUM = EnumHelper.addArmorMaterial("armormateral_mazurium", "armormaterial_mazurium", ConfigHandler.mazuriumSettings.overallResistance, new int[]{ConfigHandler.mazuriumSettings.helmetResistance, ConfigHandler.mazuriumSettings.chestplateResistance, ConfigHandler.mazuriumSettings.leggingsResistance, ConfigHandler.mazuriumSettings.bootsResistance}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ConfigHandler.mazuriumSettings.overallToughness).setRepairItem(new ItemStack(ModItems.MAZURIUM));


    //Items
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item LABATIUM = new ItemGlowingBase("labatium");
    public static final Item MAZURIUM = new ItemGlowingBase("mazurium");
    public static final Item LITTLE_LABATIUM_DUST = new ItemBase("little_labatium_dust");
    public static final Item LITTLE_MAZURIUM_DUST = new ItemBase("little_mazurium_dust");
    public static final Item LABATIUM_DUST = new ItemBase("labatium_dust");
    public static final Item MAZURIUM_DUST = new ItemBase("mazurium_dust");

    public static final Item ITEM_VAN_SHOOT = new ItemShoot("van_shoot_low");

    //Tools
    public static final ItemPickaxe LABATIUM_PICKAXE = new ToolPickaxe("labatium_pickaxe", MATERIAL_LABATIUM);
    public static final ItemPickaxe LABATIUM_HOE = new ToolPickaxe("labatium_hoe", MATERIAL_LABATIUM);
    public static final ItemSword LABATIUM_SWORD = new ToolSword("labatium_sword", MATERIAL_LABATIUM);
    public static final ItemAxe LABATIUM_AXE = new ToolAxe("labatium_axe", MATERIAL_LABATIUM);
    public static final ItemSpade LABATIUM_SHOVEL = new ToolSpade("labatium_shovel", MATERIAL_LABATIUM);

    public static final ItemPickaxe MAZURIUM_PICKAXE = new ToolPickaxe("mazurium_pickaxe", MATERIAL_MAZURIUM);
    public static final ItemPickaxe MAZURIUM_HOE = new ToolPickaxe("mazurium_hoe", MATERIAL_MAZURIUM);
    public static final ItemSword MAZURIUM_SWORD = new ToolSword("mazurium_sword", MATERIAL_MAZURIUM);
    public static final ItemAxe MAZURIUM_AXE = new ToolAxe("mazurium_axe", MATERIAL_MAZURIUM);
    public static final ItemSpade MAZURIUM_SHOVEL = new ToolSpade("mazurium_shovel", MATERIAL_MAZURIUM);

    //Armor
    public static final ItemArmor LABATIUM_BOOTS = new ArmorBase("labatium_boots", ARMOR_LABATIUM, 1, EntityEquipmentSlot.FEET);
    public static final ItemArmor LABATIUM_LEGGS = new ArmorBase("labatium_leggings", ARMOR_LABATIUM, 2, EntityEquipmentSlot.LEGS);
    public static final ChestplateBase LABATIUM_CHESTPLATE = new ChestplateBase("labatium_chestplate", ARMOR_LABATIUM, 1, EntityEquipmentSlot.CHEST);
    public static final ItemArmor LABATIUM_HEAD = new ArmorBase("labatium_helmet", ARMOR_LABATIUM, 1, EntityEquipmentSlot.HEAD);

    public static final ItemArmor MAZURIUM_BOOTS = new ArmorBase("mazurium_boots", ARMOR_MAZURIUM, 1, EntityEquipmentSlot.FEET);
    public static final ItemArmor MAZURIUM_LEGGS = new ArmorBase("mazurium_leggings", ARMOR_MAZURIUM, 2, EntityEquipmentSlot.LEGS);
    public static final ChestplateBase MAZURIUM_CHESTPLATE = new ChestplateBase("mazurium_chestplate", ARMOR_MAZURIUM, 1, EntityEquipmentSlot.CHEST);
    public static final ItemArmor MAZURIUM_HEAD = new ArmorBase("mazurium_helmet", ARMOR_MAZURIUM, 1, EntityEquipmentSlot.HEAD);

    //Seeds
    public static final Item LABATIUM_FRUIT_SEED = new LabatiumFruitSeed("labatium_fruit_seed");
    public static final Item MAZURIUM_FRUIT_SEED = new MazuriumFruitSeed("mazurium_fruit_seed");

    //food
    public static final Item LABATIUM_FRUIT = new ItemLabatiumFruit("labatium_fruit", 1, false);
    public static final Item MAZURIUM_FRUIT = new ItemMazuriumFruit("mazurium_fruit", 1, false);
    //Records
    public static final Item UNTITLED_RECORD = new UntitledRecord("untitled_record", ModSounds.untitledRecord);

}

package ua.andrey08xtomyoll.mineadditions.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import ua.andrey08xtomyoll.mineadditions.items.ItemBase;
import ua.andrey08xtomyoll.mineadditions.items.ItemGlowingBase;
import ua.andrey08xtomyoll.mineadditions.items.ItemLabatiumFruit;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.records.RecordBase;
import ua.andrey08xtomyoll.mineadditions.items.seeds.LabatiumFruitSeed;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolAxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSpade;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSword;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    ModItems instance  = new ModItems();

    //Materials
    public static final Item.ToolMaterial MATERIAL_LABATIUM = EnumHelper.addToolMaterial("material_labatium", 4, 3000, 15.0F, 15.0F, 20);


    //Items
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item LABATIUM = new ItemGlowingBase("labatium");
    public static final Item MAZURIUM = new ItemGlowingBase("mazurium");
    public static final Item LITTLE_LABATIUM_DUST = new ItemBase("little_labatium_dust");
    public static final Item LITTLE_MAZURIUM_DUST = new ItemBase("little_mazurium_dust");
    public static final Item LABATIUM_DUST = new ItemBase("labatium_dust");
    public static final Item MAZURIUM_DUST = new ItemBase("mazurium_dust");

    public static final Item ITEM_SHOOT = new ItemBase("shoot_low");

    //Tools
    public static final ItemPickaxe LABATIUM_PICKAXE = new ToolPickaxe("labatium_pickaxe", MATERIAL_LABATIUM);
    public static final ItemSword LABATIUM_SWORD = new ToolSword("labatium_sword", MATERIAL_LABATIUM);
    public static final ItemAxe LABATIUM_AXE = new ToolAxe("labatium_axe", MATERIAL_LABATIUM);
    public static final ItemSpade LABATIUM_SHOVEL = new ToolSpade("labatium_shovel", MATERIAL_LABATIUM);

    //Armor
    public static final ItemArmor.ArmorMaterial ARMOR_LABATIUM = EnumHelper.addArmorMaterial("armormateral_labatium", "armormaterial_labatium", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));
    public static final ItemArmor LABATIUM_BOOTS = new ArmorBase("labatium_boots", ARMOR_LABATIUM, 1, EntityEquipmentSlot.FEET);
    public static final ItemArmor LABATIUM_LEGGS = new ArmorBase("labatium_leggings", ARMOR_LABATIUM, 2, EntityEquipmentSlot.LEGS);
    public static final ItemArmor LABATIUM_CHESTPLATE = new ArmorBase("labatium_chestplate", ARMOR_LABATIUM, 1, EntityEquipmentSlot.CHEST);
    public static final ItemArmor LABATIUM_HEAD = new ArmorBase("labatium_helmet", ARMOR_LABATIUM, 1, EntityEquipmentSlot.HEAD);

    //Seeds
    public static final Item LABATIUM_FRUIT_SEED = new LabatiumFruitSeed("labatium_fruit_seed");

    //food
    public static final Item LABATIUM_FRUIT = new ItemLabatiumFruit("labatium_fruit", 1, false);

    //Records
    public static final Item UNTITLED_RECORD = new RecordBase("untitled_record", ModSounds.untitled_record);

}

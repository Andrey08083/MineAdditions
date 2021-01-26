package ua.andrey08xtomyoll.mineadditions.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import ua.andrey08xtomyoll.mineadditions.items.ItemGlowingBase;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolAxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSpade;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSword;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    //Materials
    public static final Item.ToolMaterial MATERIAL_LABATIUM = EnumHelper.addToolMaterial("material_labatium", 4, 3000, 15.0F, 15.0F, 20);


    //Items
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item LABATIUM = new ItemGlowingBase("labatium");

    //Tools
    public static final ItemPickaxe LABATIUM_PICKAXE = new ToolPickaxe("labatium_pickaxe", MATERIAL_LABATIUM);
    public static final ItemSword LABATIUM_SWORD = new ToolSword("labatium_sword", MATERIAL_LABATIUM);
    public static final ItemAxe LABATIUM_AXE = new ToolAxe("labatium_axe", MATERIAL_LABATIUM);
    public static final ItemSpade LABATIUM_SHOVEL = new ToolSpade("labatium_shovel", MATERIAL_LABATIUM);

    //Armor
    public static final ItemArmor.ArmorMaterial ARMOR_LABATIUM = EnumHelper.addArmorMaterial("armormateral_labatium", "armormaterial_labatium", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));
    public static final Item BOOTS = new ArmorBase("boots", ARMOR_LABATIUM, 1, EntityEquipmentSlot.FEET);
    public static final Item LEGGS = new ArmorBase("leggs", ARMOR_LABATIUM, 2, EntityEquipmentSlot.LEGS);
    public static final Item CHESTPLATE = new ArmorBase("chestplate", ARMOR_LABATIUM, 1, EntityEquipmentSlot.CHEST);
    public static final Item HEAD = new ArmorBase("head", ARMOR_LABATIUM, 1, EntityEquipmentSlot.HEAD);

}

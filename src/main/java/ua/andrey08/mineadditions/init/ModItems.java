package ua.andrey08.mineadditions.init;

import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08.mineadditions.items.ItemGlowingBase;
import ua.andrey08.mineadditions.items.tools.ToolAxe;
import ua.andrey08.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08.mineadditions.items.tools.ToolSpade;
import ua.andrey08.mineadditions.items.tools.ToolSword;

import java.util.ArrayList;
import java.util.List;

public class ModItems
{

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
}

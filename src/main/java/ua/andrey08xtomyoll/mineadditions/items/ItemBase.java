package ua.andrey08xtomyoll.mineadditions.items;

import ua.andrey08xtomyoll.mineadditions.Main;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }
}

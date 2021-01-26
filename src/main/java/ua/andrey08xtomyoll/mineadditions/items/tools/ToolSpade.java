package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.Main;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ToolSpade extends ItemSpade implements IHasModel
{
    public ToolSpade(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }
}

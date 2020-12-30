package ua.andrey08.mineadditions.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08.mineadditions.Main;
import ua.andrey08.mineadditions.init.ModItems;
import ua.andrey08.mineadditions.util.IHasModel;

public class ToolAxe extends ItemAxe implements IHasModel
{
    public ToolAxe(String name, ToolMaterial material)
    {
        super(material, 15.0F, -1.5F);
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

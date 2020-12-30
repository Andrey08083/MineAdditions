package ua.andrey08.mineadditions.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08.mineadditions.Main;
import ua.andrey08.mineadditions.init.ModItems;
import ua.andrey08.mineadditions.util.IHasModel;
import com.google.common.collect.Sets;
import java.util.Set;

public class ToolPickaxe extends ItemPickaxe implements IHasModel
{
    public ToolPickaxe(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}


    /*Multitool feature
    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return Sets.newHashSet("pickaxe", "axe", "shovel");
    }*/

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }
}

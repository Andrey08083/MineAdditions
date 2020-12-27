package ua.andrey08.mineadditions.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08.mineadditions.Main;
import ua.andrey08.mineadditions.util.IHasModel;

public class ItemGlowingBase extends ItemBase implements IHasModel
{
    public ItemGlowingBase(String name)
    {
        super(name);
    }
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this,0,"inventory");
    }
}

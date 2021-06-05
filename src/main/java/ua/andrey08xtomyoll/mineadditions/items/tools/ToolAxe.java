package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас-конструтор сокири
 */
public class ToolAxe extends ItemAxe implements IHasModel, IHasEffect
{
    /**
     * Конструктор сокири
     * @param name реєстраційне ім'я інструменту
     * @param material матеріал, від якого успадковуються властивості інструменту
     */
    public ToolAxe(String name, ToolMaterial material)
    {
        super(material, 15.0F, -1.5F);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ModMain.creativeTab);
        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

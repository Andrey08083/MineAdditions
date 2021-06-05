package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас-конструктор меча
 */
public class ToolSword extends ItemSword implements IHasModel, IHasEffect
{
    /**
     * Конструктор меча
     * @param name реєстраційне ім'я інструменту
     * @param material матеріал, від якого успадковуються властивості інструменту
     */
    public ToolSword(String name, ToolMaterial material)
    {
        super(material);
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

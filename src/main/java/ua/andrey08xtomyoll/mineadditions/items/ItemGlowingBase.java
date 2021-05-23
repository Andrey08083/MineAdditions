package ua.andrey08xtomyoll.mineadditions.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ItemGlowingBase extends ItemBase implements IHasModel, IHasEffect {

    public ItemGlowingBase(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public void registerModels() {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

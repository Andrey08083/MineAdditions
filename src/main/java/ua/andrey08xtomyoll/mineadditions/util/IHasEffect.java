package ua.andrey08xtomyoll.mineadditions.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasEffect {
    @SideOnly(Side.CLIENT)
    boolean hasEffect(ItemStack stack);
}

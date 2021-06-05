package ua.andrey08xtomyoll.mineadditions.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Інтерфейс наявності свічіння
 */
public interface IHasEffect {
    /**
     * Метод, який визначає, чи є у предмета ефект свічіння
     * @param stack предмет
     * @return true, якщо є, або false, якщо нема
     */
    @SideOnly(Side.CLIENT)
    boolean hasEffect(ItemStack stack);
}

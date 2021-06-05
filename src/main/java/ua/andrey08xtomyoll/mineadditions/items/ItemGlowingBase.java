package ua.andrey08xtomyoll.mineadditions.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас реєстрації предмету з ефектом свічіння
 */
public class ItemGlowingBase extends ItemBase implements IHasModel, IHasEffect {

    /**
     * Конструктор предмету
     * @param name реєстраційне ім'я предмету
     */
    public ItemGlowingBase(String name) {
        super(name);
    }

    /**
     * Метод, який визначає, чи є у предмета ефект свічіння
     * @param stack предмет
     * @return true, якщо є, або false, якщо нема
     */
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}

package ua.andrey08xtomyoll.mineadditions.items;

import net.minecraft.item.Item;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас снаряду Вена
 */
public class ItemShoot extends Item implements IHasModel
{

    /**
     * Конструктор
     * @param name ім'я снаряду
     */
    public ItemShoot(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        ModItems.ITEMS.add(this);
    }

    /**
     * Реєстрація моделі предмета снаряду
     */
    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

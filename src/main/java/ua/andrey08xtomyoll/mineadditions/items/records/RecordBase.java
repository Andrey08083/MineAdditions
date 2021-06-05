package ua.andrey08xtomyoll.mineadditions.items.records;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас-конструктор платівок з базовим функціоналом
 */
public class RecordBase extends ItemRecord implements IHasModel {

    /**
     * Конструктор платівки
     * @param name реєстраційне ім'я
     * @param soundIn звук, який буде програватись при використанні пластинки
     */
    public RecordBase(String name, SoundEvent soundIn) {
        super(name, soundIn);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(ModMain.creativeTab);
        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

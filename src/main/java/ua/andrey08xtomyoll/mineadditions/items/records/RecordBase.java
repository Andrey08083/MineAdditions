package ua.andrey08xtomyoll.mineadditions.items.records;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class RecordBase extends ItemRecord implements IHasModel {
    public RecordBase(String name, SoundEvent soundIn) {
        super(name, soundIn);
        setRegistryName(name);
        setTranslationKey(name);
        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

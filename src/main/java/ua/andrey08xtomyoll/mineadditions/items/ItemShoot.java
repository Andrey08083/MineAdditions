package ua.andrey08xtomyoll.mineadditions.items;

import ua.andrey08xtomyoll.mineadditions.ModMain;

public class ItemShoot extends ItemBase
{

    public ItemShoot(String name) {
        super(name);
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

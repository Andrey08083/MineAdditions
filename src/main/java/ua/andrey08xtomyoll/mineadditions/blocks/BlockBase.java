package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас-конструктор блоків з базовим функціоналом
 */
public class BlockBase extends Block implements IHasModel
{
    /**
     * Конструктор блоку
     * @param name реєстраційне ім'я блоку
     * @param material матеріал, від якого наслідується поведінка
     */
    public BlockBase(String name, Material material)
    {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ModMain.creativeTab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}

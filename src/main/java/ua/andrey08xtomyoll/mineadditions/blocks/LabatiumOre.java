package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;

import java.util.Random;

/**
 * Клас Лабатіумової руди
 */
public class LabatiumOre extends BlockBase
{
    /**
     * Конструктор блоку
     * @param name реєстраційне ім'я блоку
     * @param material матеріал, від якого наслідується поведінка
     */
    public LabatiumOre(String name, Material material)
    {
        super(name, material);
        setHardness(5.0F);
        setResistance(35.0F);
        setHarvestLevel("pickaxe",3);
        setCreativeTab(ModMain.creativeTab);

        this.lightValue = 4;
    }

    /**
     * Геттер предмету, який повинен випасти при добуванні руди
     * @return Лабатіумова руда
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.LABATIUM_ORE);
    }

    /**
     * Геттер кількості предметів, які повинні випасти при руйнуванні блоку
     * @return 1 блок
     */
    @Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }
}

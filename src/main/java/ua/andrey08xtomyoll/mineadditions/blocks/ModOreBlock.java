package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.material.Material;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

import java.util.Random;

/**
 * Клас блоків з руди (Лабатіум, Томіум)
 */
public class ModOreBlock extends BlockBase implements IHasModel
{
    /**
     * Конструктор блоку
     * @param name реєстраційне ім'я блоку
     * @param material матеріал, від якого наслідується поведінка
     */
    public ModOreBlock(String name, Material material)
    {
        super(name, material);
        setHardness(6.0F);
        setResistance(50.0F);
        setHarvestLevel("pickaxe",3);
        setCreativeTab(ModMain.creativeTab);
        this.lightValue = 15;
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }
}

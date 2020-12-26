package ua.andrey08.mineadditions.blocks;

import net.minecraft.block.material.Material;

public class LabatiumOre extends BlockBase
{

    public LabatiumOre(String name, Material material)
    {
        super(name, material);
        setHardness(5.0F);
        setResistance(35.0F);
        setHarvestLevel("pickaxe",3);
        this.lightValue = 4;
    }
}

package ua.andrey08.mineadditions.blocks;

import net.minecraft.block.material.Material;

public class LabatiumBlock extends BlockBase
{

    public LabatiumBlock(String name, Material material)
    {
        super(name, material);
        setHardness(6.0F);
        setResistance(50.0F);
        setHarvestLevel("pickaxe",3);
        this.lightValue = 15;
    }
}

package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

import java.util.Random;

public class ModOreBlock extends BlockBase
{

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
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return (Item) state.getBlock().getDefaultState();
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }
}

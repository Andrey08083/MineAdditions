package ua.andrey08xtomyoll.mineadditions.init;


import ua.andrey08xtomyoll.mineadditions.blocks.LabatiumBlock;
import ua.andrey08xtomyoll.mineadditions.blocks.LabatiumOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block LABATIUM_BLOCK = new LabatiumBlock("labatium_block", Material.IRON);
    public static final Block LABATIUM_ORE = new LabatiumOre("labatium_ore", Material.ROCK);
}

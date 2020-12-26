package com.minemod.mineadditions.init;


import com.minemod.mineadditions.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block LABATIUM_BLOCK = new BlockBase("labatium_block", Material.IRON);
    //public static final Block LABATIUM_ORE = new BlockBase("labatium_ore", Material.ROCK);
}

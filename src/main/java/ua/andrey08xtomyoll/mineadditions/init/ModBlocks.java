package ua.andrey08xtomyoll.mineadditions.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ua.andrey08xtomyoll.mineadditions.blocks.BlockThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.blocks.LabatiumBlock;
import ua.andrey08xtomyoll.mineadditions.blocks.LabatiumOre;
import ua.andrey08xtomyoll.mineadditions.blocks.MazuriumOre;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.ThermalCrusher;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block LABATIUM_BLOCK 	= new LabatiumBlock("labatium_block", Material.IRON);
    public static final Block LABATIUM_ORE 		= new LabatiumOre("labatium_ore", Material.ROCK);
    public static final Block MAZURIUM_ORE 		= new MazuriumOre("mazurium_ore", Material.ROCK);
    public static final Block THERMAL_CRUSHER = new BlockThermalCrusher("thermalcrusher", Material.ROCK, false);
    public static final Block THERMAL_CRUSHER_ON = new BlockThermalCrusher("thermalcrusher_on", Material.ROCK, true);
	
    public static void register()
    {
    	GameRegistry.registerTileEntity(ThermalCrusher.class, THERMAL_CRUSHER.getRegistryName().toString());

	}
}

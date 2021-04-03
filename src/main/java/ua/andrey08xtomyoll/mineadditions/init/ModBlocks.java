package ua.andrey08xtomyoll.mineadditions.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ua.andrey08xtomyoll.mineadditions.blocks.*;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.ThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.TileAlchemyExtractor;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block LABATIUM_BLOCK 	= new LabatiumBlock("labatium_block", Material.IRON);
    public static final Block LABATIUM_ORE 		= new LabatiumOre("labatium_ore", Material.ROCK);
    public static final Block MAZURIUM_ORE 		= new MazuriumOre("mazurium_ore", Material.ROCK);
    public static final Block THERMALCRUSHER = new BlockThermalCrusher("thermalcrusher", Material.IRON, false);
    public static final Block THERMALCRUSHER_ON = new BlockThermalCrusher("thermalcrusher_on", Material.IRON, true);
    public static final Block LABATIUM_FRUIT_CROP = new LabatiumFruitCrop("labatium_fruit_crop");
    public static final Block MAZURIUM_FRUIT_CROP = new MazuriumFruitCrop("mazurium_fruit_crop");
    public static final Block ALCHEMY_EXTRACTOR = new BlockAlchemyExtractor("alchemy_extractor", Material.IRON, false);
    public static final Block ALCHEMY_EXTRACTOR_ON = new BlockAlchemyExtractor("alchemy_extractor_on", Material.IRON, true);
	
    public static void register()
    {
    	GameRegistry.registerTileEntity(ThermalCrusher.class, THERMALCRUSHER_ON.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileAlchemyExtractor.class, ALCHEMY_EXTRACTOR_ON.getRegistryName().toString());
	}
}

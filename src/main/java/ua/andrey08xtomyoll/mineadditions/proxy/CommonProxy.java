package ua.andrey08xtomyoll.mineadditions.proxy;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.entity.EntityVan;
import ua.andrey08xtomyoll.mineadditions.handlers.GuiHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
    {
		ModBlocks.register();
    }

    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(ModMain.instance, new GuiHandler());
        Biome[] spawnBiomes = {Biome.REGISTRY.getObject(new ResourceLocation("plains")), Biome.REGISTRY.getObject(new ResourceLocation("desert")),
                Biome.REGISTRY.getObject(new ResourceLocation("extreme_hills")), Biome.REGISTRY.getObject(new ResourceLocation("frozen_ocean")),
                Biome.REGISTRY.getObject(new ResourceLocation("ice_mountains")), Biome.REGISTRY.getObject(new ResourceLocation("beaches")),
                Biome.REGISTRY.getObject(new ResourceLocation("mesa_clear_rock")), Biome.REGISTRY.getObject(new ResourceLocation("mutated_plains")),
                Biome.REGISTRY.getObject(new ResourceLocation("mutated_desert")),
                Biome.REGISTRY.getObject(new ResourceLocation("mutated_redwood_taiga")),
                Biome.REGISTRY.getObject(new ResourceLocation("mutated_savanna")),
                Biome.REGISTRY.getObject(new ResourceLocation("mutated_savanna_rock")),
                Biome.REGISTRY.getObject(new ResourceLocation("mutated_mesa")),};
        EntityRegistry.addSpawn(EntityVan.class, 60, 1, 3, EnumCreatureType.MONSTER, spawnBiomes);

    }
    
    public void postInit(FMLPostInitializationEvent event) {

    }
    
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}

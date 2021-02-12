package ua.andrey08xtomyoll.mineadditions.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ua.andrey08xtomyoll.mineadditions.ModMain;
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
    }
    
    public void postInit(FMLPostInitializationEvent event) {
    	//
    }
    
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}

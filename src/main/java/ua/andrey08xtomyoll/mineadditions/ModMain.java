package ua.andrey08xtomyoll.mineadditions;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ua.andrey08xtomyoll.mineadditions.network.NetworkUtils;
import ua.andrey08xtomyoll.mineadditions.proxy.CommonProxy;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherBurnRecipies;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import ua.andrey08xtomyoll.mineadditions.world.ModWorldGen;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ModMain
{
    @Mod.Instance
    public static ModMain instance;
    
    // Пакетная система
    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
        
        // База для пакетной системы
        ModMain.network = NetworkRegistry.INSTANCE.newSimpleChannel("TomChannel");
		NetworkUtils.registerMessages();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event)
    {
    	TCrusherBurnRecipies.init();
    	proxy.postInit(event);
    }

    public static void log(String text){
    	if(Reference.DEBUG){
    		System.out.println(text);
    	}
    }
}

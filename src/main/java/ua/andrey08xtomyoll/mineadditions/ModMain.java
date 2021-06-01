package ua.andrey08xtomyoll.mineadditions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.network.NetworkUtils;
import ua.andrey08xtomyoll.mineadditions.proxy.CommonProxy;
import ua.andrey08xtomyoll.mineadditions.util.AlchemyExtractorRecipies;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherRecipies;
import ua.andrey08xtomyoll.mineadditions.world.ModWorldGen;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ModMain {

    public static final CreativeTabs creativeTab = new CreativeTabs("MineAdditions") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ITEM_VAN_SHOOT);
        }
    };

    @Mod.Instance
    public static ModMain instance;

    // Пакетная система
    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
        // База для пакетной системы
        ModMain.network = NetworkRegistry.INSTANCE.newSimpleChannel("TomChannel");
        NetworkUtils.registerMessages();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
        ConfigHandler.initConfig();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        TCrusherRecipies.init();
        AlchemyExtractorRecipies.init();
        proxy.postInit(event);
    }

    public static void log(String text) {
        if (Reference.DEBUG) {
            System.out.println(text);
        }
    }
}

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

/**
 * Клас клієнт-серверного проксі
 */
public class CommonProxy
{
    /**
     * Подія передініціалізації клієнта та серверу
     * @param event подія
     */
	public void preInit(FMLPreInitializationEvent event)
    {
		ModBlocks.register();
    }

    /**
     * Подія ініціалізації клієнта та серверу
     * @param event подія
     */
    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(ModMain.instance, new GuiHandler());
    }
    /**
     * Подія післяініціалізації клієнта та серверу
     * @param event подія
     */
    public void postInit(FMLPostInitializationEvent event) {

    }

    /**
     * Метод реєстрації рендеру моделі (перевизначається, так як не повинен реєструвати моделі на стороні серверу)
     * @param item предмет
     * @param meta метадані
     * @param id ідентифікатор
     */
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}

package ua.andrey08xtomyoll.mineadditions.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ua.andrey08xtomyoll.mineadditions.entity.EntityRegistry;

/**
 * Клас клієнтного проксі
 */
public class ClientProxy extends CommonProxy
{
    /**
     * Подія передініціалізації клієнту
     * @param event подія
     */
	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
    	super.preInit(event);
        EntityRegistry.initModels();
    }

    /**
     * Подія ініціалізації клієнту
     * @param event подія
     */
    @Override
    public void init(FMLInitializationEvent event)
    {
    	super.init(event);
    }

    /**
     * Подія постініціалізації клієнту
     * @param event подія
     */
    @Override
    public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    }
    
    /**
     * Метод реєстрації рендеру моделі
     * @param item предмет
     * @param meta метадані
     * @param id ідентифікатор
     */
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item,meta,new ModelResourceLocation(item.getRegistryName(),id));
    }
}

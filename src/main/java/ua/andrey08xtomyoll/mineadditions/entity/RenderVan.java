package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.ResourceLocation;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

/**
 * Клас рендеру Вена
 */
public class RenderVan extends RenderLiving<EntityVan>
{
    private final ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID + ":textures/entity/van_texture.png");

    /**
     * Конструктор класу рендеру Вена
     */
    public RenderVan(RenderManager manager) {
        super(manager, new VanModel(), 0.5F);
    }

    public static Factory FACTORY = new Factory();

    /**
     * Геттер текстури Вена
     * @param entity екземпляр сутності
     * @return шлях до текстури
     */
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityVan entity)
    {
        return mobTexture;
    }

    /**
     * Клас-фабрика текстури Вена
     */
    public static class Factory implements IRenderFactory<EntityVan>
    {
        /**
         * Реалізація методу з IRenderFactory
         * @param manager менеджер рендеру
         * @return рендер Вена
         */
        @Override
        public Render<? super EntityVan> createRenderFor(RenderManager manager)
        {
            return new RenderVan(manager);
        }
    }
}


package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.ResourceLocation;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderVan extends RenderLiving<EntityVan>
{
    private final ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID + ":textures/entity/van_texture.png");

    public RenderVan(RenderManager manager) {
        super(manager, new VanModel(), 0.5F);
    }

    public static Factory FACTORY = new Factory();
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityVan entity)
    {
        return  mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityVan>
    {
        @Override
        public Render<? super EntityVan> createRenderFor(RenderManager manager)
        {
            return new RenderVan(manager);
        }
    }
}


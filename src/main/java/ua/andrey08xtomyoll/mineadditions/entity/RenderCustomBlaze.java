package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderCustomBlaze extends RenderLiving<EntityCustomBlaze>
{
    private ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID + ":textures/entity/skin_custom_blaze.png");

    public RenderCustomBlaze(RenderManager manager) {
        super(manager, new ModelGhast(), 0.5F);
    }

    public static Factory FACTORY = new Factory();
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityCustomBlaze entity)
    {
        return  mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityCustomBlaze>
    {
        @Override
        public Render<? super EntityCustomBlaze> createRenderFor(RenderManager manager)
        {
            return new RenderCustomBlaze(manager);
        }
    }
}


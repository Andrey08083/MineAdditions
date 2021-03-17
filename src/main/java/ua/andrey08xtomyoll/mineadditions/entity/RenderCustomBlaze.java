package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.util.Reference;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderCustomBlaze extends RenderLiving<EntityCustomBlaze>
{
    private final ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID + ":textures/entity/model_custom_blaze.png");

    public RenderCustomBlaze(RenderManager manager) {
        super(manager, new model_custom_blaze(), 0.5F);
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


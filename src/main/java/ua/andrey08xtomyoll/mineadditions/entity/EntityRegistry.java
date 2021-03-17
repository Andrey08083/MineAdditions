package ua.andrey08xtomyoll.mineadditions.entity;



import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityRegistry
{
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomBlaze.EntityArrowCustom.class, renderManager -> {
            return new RenderSnowball<EntityCustomBlaze.EntityArrowCustom>(renderManager, null, Minecraft.getMinecraft().getRenderItem()) {
                public ItemStack getStackToRender(EntityCustomBlaze.EntityArrowCustom entity) {
                    return new ItemStack(ModItems.ITEM_SHOOT, (int) (1));
                }
            };
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomBlaze.class, RenderCustomBlaze.FACTORY);
    }

    private static int ID = 1;
    public static EntityEntry CUSTOM_BLAZE = EntityEntryBuilder.create().entity(EntityCustomBlaze.class).name("Custom Blaze")
            .id("Custom Blaze", ID++).egg(0xc82020, 0x892612).tracker(160, 2, false).build();
    public static EntityEntry SNOWSHARD = EntityEntryBuilder.create()
            .entity(EntityCustomBlaze.EntityArrowCustom.class)
            .name("Snowshard")
            .id("Snowshard", ID++)
            .tracker(64, 1, true).build();

    @SubscribeEvent
    public static void registryEntity(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                SNOWSHARD,
                CUSTOM_BLAZE
        );
    }
}

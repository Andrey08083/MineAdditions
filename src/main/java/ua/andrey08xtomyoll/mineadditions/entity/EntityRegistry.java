package ua.andrey08xtomyoll.mineadditions.entity;



import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityVan.EntityVanShoot.class, renderManager -> {
            return new RenderSnowball<EntityVan.EntityVanShoot>(renderManager, null, Minecraft.getMinecraft().getRenderItem()) {
                public ItemStack getStackToRender(EntityVan.EntityVanShoot entity) {
                    return new ItemStack(ModItems.ITEM_VAN_SHOOT, (int) (1));
                }
            };
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityVan.class, RenderVan.FACTORY);
    }

    private static int ID = 1;
    public static EntityEntry VAN = EntityEntryBuilder.create().entity(EntityVan.class).name("Van")
            .id("van", ID++).egg(0xc82020, 0x892612).tracker(160, 2, false).build();
    public static EntityEntry VAN_SHOOT = EntityEntryBuilder.create()
            .entity(EntityVan.EntityVanShoot.class)
            .name("Van Shoot")
            .id("van_shoot", ID++)
            .tracker(64, 1, true).build();

    @SubscribeEvent
    public static void registryEntity(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                VAN_SHOOT,
                VAN
        );
    }
}

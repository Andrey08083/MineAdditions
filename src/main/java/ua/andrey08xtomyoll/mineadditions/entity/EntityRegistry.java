package ua.andrey08xtomyoll.mineadditions.entity;



import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
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

/**
 * Клас реєстрації створення моба
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityRegistry
{
    /**
     * Метод ініціалізації моделей
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityVan.EntityVanShoot.class, renderManager ->
                new RenderSnowball<EntityVan.EntityVanShoot>(renderManager, null, Minecraft.getMinecraft().getRenderItem()) {
            public ItemStack getStackToRender(EntityVan.EntityVanShoot entity) {
                return new ItemStack(ModItems.ITEM_VAN_SHOOT, (1));
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityVan.class, RenderVan.FACTORY);
    }

    static Biome[] spawnBiomes =
            {Biome.REGISTRY.getObject(new ResourceLocation("plains")),
            Biome.REGISTRY.getObject(new ResourceLocation("desert")),
            Biome.REGISTRY.getObject(new ResourceLocation("extreme_hills")),
            Biome.REGISTRY.getObject(new ResourceLocation("frozen_ocean")),
            Biome.REGISTRY.getObject(new ResourceLocation("ice_mountains")),
            Biome.REGISTRY.getObject(new ResourceLocation("beaches")),
            Biome.REGISTRY.getObject(new ResourceLocation("mesa_clear_rock")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_plains")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_desert")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_redwood_taiga")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_savanna")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_savanna_rock")),
            Biome.REGISTRY.getObject(new ResourceLocation("mutated_mesa")),};

    private static int ID = 1;
    public static EntityEntry VAN = EntityEntryBuilder.create()
            .entity(EntityVan.class).name("Van")
            .id("van", ID++)
            .egg(0xc82020, 0x892612)
            .tracker(160, 2, false)
            .spawn(EnumCreatureType.MONSTER, 7, 1, 2, spawnBiomes)
            .build();
    public static EntityEntry VAN_SHOOT = EntityEntryBuilder.create()
            .entity(EntityVan.EntityVanShoot.class)
            .name("Van Shoot")
            .id("van_shoot", ID++)
            .tracker(64, 1, true).build();

    /**
     * Метод реєстрації моба і снаряду
     */
    @SubscribeEvent
    public static void registryEntity(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                VAN_SHOOT,
                VAN
        );
    }

}

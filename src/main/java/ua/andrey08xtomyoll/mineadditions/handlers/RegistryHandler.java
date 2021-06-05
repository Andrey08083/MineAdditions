package ua.andrey08xtomyoll.mineadditions.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.init.ModSounds;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас з реєстраційними подіями
 */
@Mod.EventBusSubscriber
public class RegistryHandler {

    /**
     * Подія реєстрації предметів
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    /**
     * Подія реєстрації блоків
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    /**
     * Подія реєстрації моделей
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }

    /**
     * Подія реєстрації звуків
     * @param e параметр події
     */
    @SubscribeEvent
    public static void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().registerAll(ModSounds.sounds.toArray(new SoundEvent[0]));
    }
}

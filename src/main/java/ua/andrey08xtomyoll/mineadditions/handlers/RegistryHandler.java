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

@Mod.EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for (Item item : ModItems.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }

        for (Block block : ModBlocks.BLOCKS)
        {
            if (block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> e)
    {
        e.getRegistry().registerAll(ModSounds.sounds.toArray(new SoundEvent[0]));
    }
}

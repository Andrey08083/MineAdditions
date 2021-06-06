package ua.andrey08xtomyoll.mineadditions.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

/**
 * Клас рецептів термічного подрібнювачу
 */
public class TCrusherRecipies extends AlchemyExtractorRecipies
{
    /**
     * Метод ініціалізації рецептів
     */
    public static void init()
    {
		/* Рецепти складаються з:
		- int час крафту
		- Item[]{} список  вихідних предметів (до 5)
		- Item основний результат
		- Item[]{} список  суб-результатів (до 4)
		*/
        TCrusherRecipies.addRecipeWithCookTime(800, new Item[]{Item.getItemFromBlock(ModBlocks.LABATIUM_ORE)}, ModItems.LITTLE_LABATIUM_DUST, new Item[]{});
        TCrusherRecipies.addRecipeWithCookTime(800, new Item[]{Item.getItemFromBlock(ModBlocks.TOMIUM_ORE)}, ModItems.LITTLE_TOMIUM_DUST, new Item[]{});
    }

}

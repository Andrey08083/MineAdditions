package ua.andrey08xtomyoll.mineadditions.gui.containers;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

/**
 * Клас, в якому налаштовуються спеціальні слоти
 */

public class CustomSlots extends Slot
{
    int con_id;

    /**
     * Конструктор класу
     * @param inventoryIn інвентар
     * @param index індекс слота
     * @param xPosition позиція Х
     * @param yPosition позиція Y
     * @param containerId індекс контейнера
     */
    public CustomSlots(IInventory inventoryIn, int index, int xPosition, int yPosition, int containerId) {
        super(inventoryIn, index, xPosition, yPosition);
        con_id = containerId;
    }

    /**
     * Визначає, які предмети можна помістити в цей слот
     * @param itemStack стек
     * @return true для вказаного предмета
     */
    public boolean isItemValid(ItemStack itemStack)
    {
        {
            if (con_id == 0) {
                return itemStack.getItem() == Item.getItemFromBlock(ModBlocks.LABATIUM_ORE) ||
                        itemStack.getItem() == Item.getItemFromBlock(ModBlocks.TOMIUM_ORE);
            }
            if(con_id == 1)
            {
                return (new ItemStack(Items.POTIONITEM, (int) (1)).getItem() == itemStack.getItem());
            }
            if(con_id == 2)
            {
                return itemStack.getItem() == Items.WHEAT_SEEDS;
            }
            if (con_id == 3)
            {
                return itemStack.getItem() == ModItems.LITTLE_LABATIUM_DUST ||
                        itemStack.getItem() == ModItems.LITTLE_TOMIUM_DUST;
            }
            else {
                return itemStack.getItem() == Item.getItemFromBlock(ModBlocks.LABATIUM_ORE);
            }
        }
    }
}
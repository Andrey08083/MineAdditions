package ua.andrey08xtomyoll.mineadditions.gui.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;

public class ThermalCrusherInputSlot extends Slot
{

    public ThermalCrusherInputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() == Item.getItemFromBlock(ModBlocks.LABATIUM_ORE) ||
                itemStack.getItem() == Item.getItemFromBlock(ModBlocks.MAZURIUM_ORE);
    }
}

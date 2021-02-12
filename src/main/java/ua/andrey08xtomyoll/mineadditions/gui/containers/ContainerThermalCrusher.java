package ua.andrey08xtomyoll.mineadditions.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.ThermalCrusher;

public class ContainerThermalCrusher extends Container
{
    private final IInventory tileThermalCrusher;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerThermalCrusher(InventoryPlayer playerInventory, IInventory furnaceInventory)
    {
        this.tileThermalCrusher = furnaceInventory;
        ThermalCrusher thermalCrusher = (ThermalCrusher) tileThermalCrusher;
        // Входящие слоты (слева направо, в верхнем левом углу)
        for(int i = 0; i < ThermalCrusher.slotsinput; i++){
        	this.addSlotToContainer(new ThermalCrusherInputSlot(furnaceInventory, i, 57 + i * 18, 16));
        }
        // Топливо
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, ThermalCrusher.slotsinput, 57, 53));
        // Исходящие слоты (справа налево, в нижнем правом углу)
        for(int n = (ThermalCrusher.slotsinput + 1); n < ThermalCrusher.slotscount; n++){
        	this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, n, 120, 44 - (n - (ThermalCrusher.slotsinput + 1)) * 18));
        }

        // Слоты игрока
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Тулбар
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileThermalCrusher);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
    	super.detectAndSendChanges();

        for (IContainerListener icontainerlistener : this.listeners)
        {
            if (this.cookTime != this.tileThermalCrusher.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileThermalCrusher.getField(2));
            }

            if (this.furnaceBurnTime != this.tileThermalCrusher.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileThermalCrusher.getField(0));
            }

            if (this.currentItemBurnTime != this.tileThermalCrusher.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileThermalCrusher.getField(1));
            }

            if (this.totalCookTime != this.tileThermalCrusher.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileThermalCrusher.getField(3));
            }
        }

        this.cookTime = this.tileThermalCrusher.getField(2);
        this.furnaceBurnTime = this.tileThermalCrusher.getField(0);
        this.currentItemBurnTime = this.tileThermalCrusher.getField(1);
        this.totalCookTime = this.tileThermalCrusher.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileThermalCrusher.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileThermalCrusher.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (ThermalCrusher.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}

package ua.andrey08xtomyoll.mineadditions.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.TileAlchemyExtractor;

public class ContainerAlchemyExtractor extends Container
{
    private final IInventory tileentity;
    private final TileAlchemyExtractor extractor;
    private int cookTime;
    private int totalCookTime;
    private int BurnTime;
    private int currentItemBurnTime;

    public ContainerAlchemyExtractor(InventoryPlayer playerInventory, IInventory furnaceInventory)
    {
        this.tileentity = furnaceInventory;
        this.extractor = (TileAlchemyExtractor) tileentity;
        //семена
        this.addSlotToContainer(new CustomSlots(furnaceInventory, 0, 25, 14, 2));
        //пыль
        this.addSlotToContainer(new CustomSlots(furnaceInventory, 1, 10, 53, 3));
        //топливо
        this.addSlotToContainer(new CustomSlots(furnaceInventory, 2, 39, 53, 1));
        //семена
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 3, 120, 53));
        //бутылка
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 4, 120, 23));

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
        listener.sendAllWindowProperties(this, this.tileentity);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileentity.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileentity.getField(2));
            }

            if (this.BurnTime != this.tileentity.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            }

            if (this.currentItemBurnTime != this.tileentity.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileentity.getField(1));
            }

            if (this.totalCookTime != this.tileentity.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileentity.getField(3));
            }
        }

        this.cookTime = this.tileentity.getField(2);
        this.BurnTime = this.tileentity.getField(0);
        this.currentItemBurnTime = this.tileentity.getField(1);
        this.totalCookTime = this.tileentity.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileentity.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileentity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 3) {
                if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (!this.mergeItemStack(itemstack1, 0, 3, false)) {
                if (index < 3 + 27) {
                    if (!this.mergeItemStack(itemstack1, 3 + 27, this.inventorySlots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.mergeItemStack(itemstack1, 3, 3 + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

}


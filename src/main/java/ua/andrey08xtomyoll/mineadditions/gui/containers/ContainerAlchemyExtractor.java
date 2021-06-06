package ua.andrey08xtomyoll.mineadditions.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Клас контейнера тайла AlchemyExtractor
 */

public class ContainerAlchemyExtractor extends Container
{
    private final IInventory tileentity;
    private int cookTime;
    private int totalCookTime;
    private int BurnTime;
    private int currentItemBurnTime;

    /**
     * Конструктор класу
     * @param playerInventory інвентар гравця
     * @param TileInventory інвентар тайла
     */
    public ContainerAlchemyExtractor(InventoryPlayer playerInventory, IInventory TileInventory)
    {
        this.tileentity = TileInventory;
        // насіння пшениці
        this.addSlotToContainer(new CustomSlots(TileInventory, 0, 25, 14, 2));
        // Пил
        this.addSlotToContainer(new CustomSlots(TileInventory, 1, 10, 53, 3));
        // Паливо
        this.addSlotToContainer(new CustomSlots(TileInventory, 2, 39, 53, 1));
        // Насіння фрукта
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, TileInventory, 3, 120, 53));
        // Вода
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, TileInventory, 4, 120, 23));

        // Слоти гравця
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

    /**
     * Метод відслідковує зміну розмірів вікна інвентаря тайлу
     * @param listener прослуховувач
     */
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileentity);
    }

    /**
     * Шукає зміни, внесені в контейнер, надсилає їх кожному слухачеві.
     */
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

    /**
     * Визначає, чи може гравець використовувати цей контейнер
     * @param playerIn гравець
     * @return користувач може використвувати контейнер
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileentity.isUsableByPlayer(playerIn);
    }

    /**
     * Метод визначає поведінку слотів при переносі стеків за допомогою shift
     * @param playerIn гравець
     * @param index індекс слота
     * @return стек
     */
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



package ua.andrey08xtomyoll.mineadditions.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.blocks.BlockThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherRecipies;
import ua.andrey08xtomyoll.mineadditions.util.TilesFuel;

import javax.annotation.Nonnull;


/**
 * Клас тайла механізма ThermalCrusher
 * Реалізує логіку роботи маханізма
 */
@Nonnull
public class TileThermalCrusher extends TileEntity implements ITickable, ISidedInventory
{
    // Загальна кількусть слотів, не меньше 3-х, не більше 11 (5 на віхд + паливо + 5 на виході
    public static int slotscount = 4;
    // Кількість слотів на вхід ( від 1 до 5)
    public static int slotsinput = 1;

    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {slotsinput + 1, slotsinput};
    private static final int[] SLOTS_SIDES = new int[] {slotsinput};

    private NonNullList<ItemStack> CrusherItemStacks = NonNullList.withSize(slotscount, ItemStack.EMPTY);

    private int CrusherBurnTime;

    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String CustomName;

    /**
     * Геттер, який повертає розмір інвентаря
     * @return розмір інвентаря
     */
    public int getSizeInventory()
    {
        return this.CrusherItemStacks.size();
    }

    /**
     * Метод перевірки на порожність
     * @return true, якщо предметів нема, або false, якщо є хоча б один предмет
     */
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.CrusherItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Метод отримання предмету з слоту
     * @param index індекс слоту механізму
     * @return стек предметів з слоту
     */
    @Nonnull
    public ItemStack getStackInSlot(int index)
    {
        return this.CrusherItemStacks.get(index);
    }

    /**
     * Зменшує розмір стека в слоті
     * @param index індекс слота
     * @param count на скільки потрібно зменьшити розмір
     * @return стек зі зменшеним розміром
     */
    @Nonnull
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.CrusherItemStacks, index, count);
    }

    /**
     * Видаляє весь стек предметів зі слота
     * @param index індекс слота
     * @return видалення слота
     */
    @Nonnull
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.CrusherItemStacks, index);
    }

    /**
     * Сеттер стеку предметів по індексу в механізмі
     * @param index індекс слоту в механізмі
     * @param stack стек предметів
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.CrusherItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.CrusherItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index >= 0 && index < slotsinput && !flag)
        {
            this.totalCookTime = TCrusherRecipies.getCookTimeForItems(getInputSlots());
            this.cookTime = 0;
            this.markDirty();
        }
    }

    /**
     * Отримує назву контейнера
     * @return якщо назви для контейнера не встановлено, йому дається вказана назва
     */
    @Nonnull
    public String getName()
    {
        return this.hasCustomName() ? this.CustomName : "container.thermal_crusher";
    }

    /**
     * Метод перевірки наявності модифікованого імені механізму
     * @return true, якщо ім'я модифіковано, або false, якщо ім'я не відредаговано
     */
    public boolean hasCustomName()
    {
        return this.CustomName != null && !this.CustomName.isEmpty();
    }

    /**
     * Сеттер модифікованого імені інвентаря механізму
     * @param name ім'я механізму
     */
    public void setCustomInventoryName(String name)
    {
        this.CustomName = name;
    }

    /**
     * Читає стан параметрів тайла з NBT
     * @param compound NBT-тег, з якого необхідно отримати параметри
     */
    public void readFromNBT(NBTTagCompound  compound)
    {
        super.readFromNBT(compound);
        this.CrusherItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.CrusherItemStacks);
        this.CrusherBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.CrusherItemStacks.get(1));

        if (compound.hasKey("CustomName", 8))
        {
            this.CustomName = compound.getString("CustomName");
        }
    }
    /**
     * Записує стан параметрів тайла в NBT
     * @param compound NBT-тег, в який необхідно записати параметри
     */
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.CrusherBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.CrusherItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.CustomName);
        }

        return compound;
    }
    /**
     * Геттер максимального ліміту предметів в стаку
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Метод перевірки на те, чи проводиться якийсь крафт в даний момент часу
     * @return true, якщо так, або false, якщо ні
     */
    public boolean isBurning()
    {
        return this.CrusherBurnTime > 0;
    }

    /**
     * Статичний метод перевірки на те, чи проводиться якийсь крафт в даний момент часу
     * @return true, якщо так, або false, якщо ні
     */
    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    /**
     * Метод визначає, чи є сировина у вхідних слотах
     * @return чи є сировина в слотах
     */
    private boolean isSomethingInput(){
        boolean isSomething = false;
        for(int i = 0; i < slotsinput; i++){
            if(!this.CrusherItemStacks.get(i).isEmpty())
            {
                isSomething = true;
                break;
            }
        }
        return isSomething;
    }
    /**
     * Геттер ідентифікатору слоту для палива
     * @return ідентифікатор слоту для палива
     */
    private int getFuelSlotId(){
        return slotsinput;
    }

    /**
     * Оновлює стан тайла кожен тік.
     */
    public void update()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        // Якщо тайл працює, повільно витрачаємо паливо
        if (this.isBurning())
        {
            --this.CrusherBurnTime;
        }

        if (!this.world.isRemote)
        {
            // Отриуємо стек палива
            ItemStack fuel = this.CrusherItemStacks.get(getFuelSlotId());

            if (this.isBurning() || !fuel.isEmpty() && isSomethingInput())
            {
                // Якщо тайл працює і рецепт проходить перевірку, то повільно витарчаємо паливо
                if (!this.isBurning() && this.canSmelt())
                {
                    this.CrusherBurnTime = getItemBurnTime(fuel);
                    this.currentItemBurnTime = this.CrusherBurnTime;

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (!fuel.isEmpty())
                        {
                            Item item = fuel.getItem();
                            fuel.shrink(1);

                            if (fuel.isEmpty())
                            {
                                ItemStack fuelStack = item.getContainerItem(fuel);
                                this.CrusherItemStacks.set(getFuelSlotId(), fuelStack);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ModMain.log("Процесс крафту: " + this.cookTime + " з " + this.totalCookTime);
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = TCrusherRecipies.getCookTimeForItems(getInputSlots());
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    ModMain.log("Тайл не працює, або неможливо провести процес");
                    this.cookTime = 0;
                }
            }
            else if (!this.isBurning() && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockThermalCrusher.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    /**
     * Геттер списку активних слотів для вхідних інгрідієнтів
     * @return список активних слотів для вхідних інгрідієнтів
     */
    private List<Item> getInputSlots(){

        ArrayList<Item> inputItemsStacks = new ArrayList<>();
        for(int i = 0; i < slotsinput; i++){
            if(!this.CrusherItemStacks.get(i).isEmpty())
                inputItemsStacks.add(this.CrusherItemStacks.get(i).getItem());
        }

        return inputItemsStacks;
    }

    /**
     * Перевірка на те, чи можна провести такий рецепт
     * @return true, якщо так, або false, якщо ні
     */
    private boolean canSmelt()
    {
        boolean inputChecker = false;
        for(int i = 0; i < slotsinput; i++){
            if (!this.CrusherItemStacks.get(i).isEmpty())
            {
                inputChecker = true;
                break;
            }
        }

        if(!inputChecker)
            return false;

        ItemStack result = new ItemStack(TCrusherRecipies.getResultForItems(getInputSlots()));

        if (result.isEmpty()) return false;
        else
        {
            ItemStack resultSlot = this.CrusherItemStacks.get(slotsinput + 1);
            if (resultSlot.isEmpty())
                return true;
            else if (!resultSlot.isItemEqual(result))
                return false;
            else if (resultSlot.getCount() + result.getCount() <= this.getInventoryStackLimit() && resultSlot.getCount() + result.getCount() <= resultSlot.getMaxStackSize())
                return true;
            else
                return resultSlot.getCount() + result.getCount() <= result.getMaxStackSize();
        }
    }

    /**
     * Метод завершення циклу переплавки
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            int resultSlotId = slotsinput + 1;
            List<Item> input = getInputSlots();
            ItemStack result = new ItemStack(TCrusherRecipies.getResultForItems(input));
            ItemStack resultSlot = this.CrusherItemStacks.get(resultSlotId);
            List<Item> output = TCrusherRecipies.getSubsForItems(input);

            // Основний результат
            if (resultSlot.isEmpty())
            {
                this.CrusherItemStacks.set(resultSlotId, result.copy());
            }
            else if (resultSlot.getItem() == result.getItem())
            {
                resultSlot.grow(result.getCount());
            }

            for(int item = 0; item < output.size(); item++)
            {
                if(output.get(item) == null)
                    continue;
                for(int slot = resultSlotId + 1; slot < slotscount; slot++)
                {
                    if (this.CrusherItemStacks.get(slot).isEmpty())
                    {
                        this.CrusherItemStacks.set(slot, new ItemStack(output.get(item)));
                        break;
                    }
                    else if (this.CrusherItemStacks.get(slot).getItem() == output.get(item))
                    {
                        this.CrusherItemStacks.get(slot).grow(1);
                        break;
                    }
                }
            }

            for(int i = 0; i < slotsinput; i++){
                if(!this.CrusherItemStacks.get(i).isEmpty())
                    this.CrusherItemStacks.get(i).shrink(1);
            }
        }
    }


    /**
     * Визначає, скільки може горіти паливо
     * @param stack стек паливо
     * @return повертає час горіння для даного палива
     */
    public static int getItemBurnTime(ItemStack stack)
    {
        return TilesFuel.getFuelTime(stack, 1);
    }

    /**
     * Метод перевірки на те, чи являється предмет в слоті для палива, паливом
     * @param stack вхідний предмет
     * @return true, якщо предмет - паливо, або false, якщо предмет - не паливо
     */
    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

    /**
     * Метод перевірки на те, чи може бути цей блок використаний гравцем
     * @param player гравець
     * @return true, якщо гравець знаходиться на дозволеній відстані, або false, якщо гравець занадто далеко
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Метод відкриття інвентарю гравцем (повинен бути реалізований, так як наслідується від інтерфейсу)
     * @param player гравець
     */
    public void openInventory(EntityPlayer player)
    {
    }

    /**
     * Метод закриття інвентарю гравцем (повинен бути реалізований, так як наслідується від інтерфйесу)
     * @param player гравець
     */
    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Метод перевірки на те, чи може цей предмет бути в даному слоті
     * @param index індекс слоту
     * @param stack предмет
     * @return true, якщо так, або false, якщо ні
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 2)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.CrusherItemStacks.get(1);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    /**
     * Метод отримання слотів, які можуть бути доступними з різних сторін (потрібно для воронки і подібних блоків)
     * @param side сторона, до якої приєднаний блок
     * @return масив індексів слотів, які доступні для блоку
     */
    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return SLOTS_BOTTOM;
        }
        else
        {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    /**
     * Метод перевірки на те, чи може цей предмет потрапити в механізм
     * @param index індекс слота
     * @param itemStackIn предмет
     * @param direction сторона блоку
     * @return true, якщо так, або false, якщо ні
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Метод перевірки на те, чи може цей предмет покинути механізм
     * @param index індекс комірки
     * @param stack предмет
     * @param direction сторона блоку
     * @return true, якщо так, або false, якщо ні
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            return item == Items.WATER_BUCKET || item == Items.BUCKET;
        }

        return true;
    }

    /**
     * Отримаує параметри тайла за індексом
     * @param id номер індекса
     * @return поле, яке відповідає даному індексу
     */
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.CrusherBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }
    /**
     * Встановлює індекси для параметрів тайла, а також значення полів
     * @param id індекс
     * @param value значення
     */
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.CrusherBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    /**
     * Метод визначає, скільки полів параметрів є у тайла
     * @return кількість параметрів
     */
    public int getFieldCount()
    {
        return 4;
    }

    /**
     * Очищає всі слоти від стеків
     */
    public void clear()
    {
        this.CrusherItemStacks.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    /**
     * Метод отримання властивостей відносно сторони механізму, з якої зроблено звернення до нього
     * @param capability властивість
     * @param facing сторона
     * @param <T> об'єкт, який реалізує інтерфейс IItemHandler
     * @return об'єкт, який реалізує інтерфейс IItemHandler
     */
    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}
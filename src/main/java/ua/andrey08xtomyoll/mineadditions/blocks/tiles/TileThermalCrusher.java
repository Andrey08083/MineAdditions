package ua.andrey08xtomyoll.mineadditions.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
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
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherRecipies;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

import javax.annotation.Nonnull;

import static ua.andrey08xtomyoll.mineadditions.util.TilesFuel.getItemBurnTime;

/**
 * Клас блоку-сутності механізму Thermal Crusher
 */
@Nonnull
public class TileThermalCrusher extends TileEntity implements ITickable, ISidedInventory
{
    /** Величины, которые задают кол-во слотов на выход/выход и соотвественно саму математику **/
    // Общее кол-во слотов, не менее 3х, не более 11 (5 входящих + топливо + 5 на выходе)
    public static int slotscount = 4;
    // Кол-во входящих (от 1 до 5, с ориентацией на общее кол-во)
    public static int slotsinput = 1;

    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {slotsinput + 1, slotsinput};
    private static final int[] SLOTS_SIDES = new int[] {slotsinput};

    // Все слоты. Сначала входящие, затем топливо, затем результат, затем суб-продукты
    private NonNullList<ItemStack> CrusherItemStacks = NonNullList.withSize(slotscount, ItemStack.EMPTY);

    private int CrusherBurnTime;

    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String CustomName;

    /**
     * Геттер, який повертає розмір інвентарю
     * @return розмір інвентарю
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
     * @return предмет з слоту
     */
    @Nonnull
    public ItemStack getStackInSlot(int index)
    {
        return this.CrusherItemStacks.get(index);
    }

    /**
     * Метод для розділення предметів в стаку
     * @param index індекс слоту, в якому знаходиться стак
     * @param count кількість
     * @return розділений стак
     */
    @Nonnull
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.CrusherItemStacks, index, count);
    }

    /**
     * Метод для забирання предмету з слоту
     * @param index індекс слоту, в якому знаходиться стак
     * @return стак без частини предметів
     */
    @Nonnull
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.CrusherItemStacks, index);
    }

    /**
     * Сеттер стаку предметів по індексу в механізмі
     * @param index індекс слоту в механізмі
     * @param stack стак предметів
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
        // Сбрасываем время, если меняются входящие
        if (index >= 0 && index < slotsinput && !flag)
        {
            this.totalCookTime = TCrusherRecipies.getCookTimeForItems(getInputSlots());
            this.cookTime = 0;
            this.markDirty();
        }
    }

    /**
     * Геттер імені механізму
     * @return ім'я механізму
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
     * Метод присвоєння даних з NBT-тегу
     * @param compound NBT-тег
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
     * Метод запису даних в NBT-тег
     * @param compound NBT-тег
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
     * Метод перевірки на те, чи є якісь предмети в механізмі
     * @return true, якщо так, або false, якщо ні
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
     * Метод, який виконується кожен ігровий тік
     */
    public void update()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        // Если печь горит, медленно тратим топливо
        if (this.isBurning())
        {
            --this.CrusherBurnTime;
        }

        if (!this.world.isRemote)
        {
            // Получаем стак в слоте топлива
            ItemStack fuel = this.CrusherItemStacks.get(getFuelSlotId());

            if (this.isBurning() || !fuel.isEmpty() && isSomethingInput())
            {
                // Если горит и рецепт проходит проверку, то тратим топливо
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
                    // Наблюдаем за процессом
                    ModMain.log("Процесс плавления: " + this.cookTime + " из " + this.totalCookTime);
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
                    // Если процесс не идёт, сбрасываем время
                    ModMain.log("Печь не горит, либо невозможно произвести прцоесс");
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
    // Получаем список активных слотов входящих веществ
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
    // Проверка на то, можно ли провести такой рецепт
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
            ItemStack resultSlot = this.CrusherItemStacks.get(slotsinput + 1); // берём слот выше входящих + топливо
            if (resultSlot.isEmpty())
                return true;
            else if (!resultSlot.isItemEqual(result))
                return false;
            else if (resultSlot.getCount() + result.getCount() <= this.getInventoryStackLimit() && resultSlot.getCount() + result.getCount() <= resultSlot.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                return true;
            else
                return resultSlot.getCount() + result.getCount() <= result.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
        }
    }

    /**
     * Метод завершення циклу переплавки
     */
    // Конец переплавки (цикла)
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            int resultSlotId = slotsinput + 1;
            List<Item> input = getInputSlots();
            ItemStack result = new ItemStack(TCrusherRecipies.getResultForItems(input));
            ItemStack resultSlot = this.CrusherItemStacks.get(resultSlotId);
            List<Item> output = TCrusherRecipies.getSubsForItems(input);

            // Основной результат
            if (resultSlot.isEmpty())
            {
                this.CrusherItemStacks.set(resultSlotId, result.copy());
            }
            else if (resultSlot.getItem() == result.getItem())
            {
                resultSlot.grow(result.getCount());
            }

            // Добрасываем суб-продукты в слоты исходящих
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

    // Время пережигания, вынесено в отдельный класс

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
     * Метод перевірки на те, чи може бути цей блок аикористаний гравцем
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
     * Метод відкриття інвентарю гравцем (повинен бути реалізований, так як наслідується від абстрактного класу)
     * @param player гравець
     */
    public void openInventory(EntityPlayer player)
    {
    }

    /**
     * Метод закриття інвентарю гравцем (повинен бути реалізований, так як наслідується від абстрактного класу)
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
     * @return масив індексів комірок, які доступні для блоку
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
     * @param index індекс комірки
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

    public String getGuiID()
    {
        return Reference.MOD_ID + ":thermalcrusher";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerThermalCrusher(playerInventory, this);
    }

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
     * Сеттер поля класу по ідентифікатору
     * @param id ідентифікатор
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
     * Геттер кількості полей
     * @return кількість полей
     */
    public int getFieldCount()
    {
        return 4;
    }

    /**
     * Метод очистки комірок механізму
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
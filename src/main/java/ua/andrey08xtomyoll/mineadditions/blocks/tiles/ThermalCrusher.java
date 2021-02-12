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
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.blocks.BlockThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherBurnRecipies;
import ua.andrey08xtomyoll.mineadditions.util.TCrusherFuel;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

public class ThermalCrusher extends TileEntity implements ITickable, ISidedInventory
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
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(slotscount, ItemStack.EMPTY);
    
    private int furnaceBurnTime;
    
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
	private String furnaceCustomName;
	
	public int getSizeInventory()
    {
        return this.furnaceItemStacks.size();
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.furnaceItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks.get(index);
    }
    
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.furnaceItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.furnaceItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
        // Сбрасываем время, если меняются входящие
        if (index >= 0 && index < this.slotsinput && !flag)
        {
        	this.totalCookTime = TCrusherBurnRecipies.getCookTimeForItems(getInputSlots());
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName()
    {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }
    
    public boolean hasCustomName()
    {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String name)
    {
        this.furnaceCustomName = name;
    }

    public static void registerFixesFurnace(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(ThermalCrusher.class, new String[] {"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

        if (compound.hasKey("CustomName", 8))
        {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.furnaceCustomName);
        }

        return compound;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    // Проверяем, есть ли что-то внутри
    private boolean isSomethingInput(){
    	boolean isSomething = false;
    	for(int i = 0; i < this.slotsinput; i++){
    		if(!this.furnaceItemStacks.get(i).isEmpty())
			{
				isSomething = true;
				break;
			}
    	}
    	return isSomething;
    }
    
    private int getFuelSlotId(){
    	return this.slotsinput;
    }
    
    // Основной метод цикла
    public void update()
    {
    	//  Лог для слотов, сугубо на отладку
    	ModMain.log("======== Состояние =========");
    	for(int l = 0; l < this.slotscount; l++){
    		ModMain.log("Слот " + l + ": " + (this.furnaceItemStacks.get(l).isEmpty() ? "Пусто" : this.furnaceItemStacks.get(l).getDisplayName()));
    	}
    	
        boolean flag = this.isBurning();
        boolean flag1 = false;

        // Если печь горит, медленно тратим топливо
        if (this.isBurning())
        {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote)
        {
        	// Получаем стак в слоте топлива
        	ItemStack fuel = this.furnaceItemStacks.get(getFuelSlotId());

            if (this.isBurning() || !fuel.isEmpty() && isSomethingInput())
            {
            	// Если горит и рецепт проходит проверку, то тратим топливо
                if (!this.isBurning() && this.canSmelt())
                {
                    this.furnaceBurnTime = getItemBurnTime(fuel);
                    this.currentItemBurnTime = this.furnaceBurnTime;

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
                                this.furnaceItemStacks.set(getFuelSlotId(), fuelStack);
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
                        this.totalCookTime = TCrusherBurnRecipies.getCookTimeForItems(getInputSlots());
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

    // Получаем список активных слотов входящих веществ
	private List<Item> getInputSlots(){
    	
    	ArrayList<Item> inputItemsStacks = new ArrayList<Item>();
    	for(int i = 0; i < slotsinput; i++){
    		if(!this.furnaceItemStacks.get(i).isEmpty())
    			inputItemsStacks.add(this.furnaceItemStacks.get(i).getItem());
    	}
    	
    	return inputItemsStacks;
    }
    
    public int getCookTime(NonNullList<Item> stacks)
    {
    	return TCrusherBurnRecipies.getCookTimeForItems(stacks);
    }
    
    // Проверка на то, можно ли провести такой рецепт
    private boolean canSmelt()
    {
    	boolean inputChecker = false;
    	for(int i = 0; i < this.slotsinput; i++){
			if (!this.furnaceItemStacks.get(i).isEmpty())
	        {
	            inputChecker = true;
	            break;
	        }
		}
		
		if(!inputChecker)
			return false;
    		
		ItemStack result = new ItemStack(TCrusherBurnRecipies.getResultForItems(getInputSlots()));

        if (result.isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack resultSlot = this.furnaceItemStacks.get(this.slotsinput + 1); // берём слот выше входящих + топливо
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
    
    // Конец переплавки (цикла)
    public void smeltItem()
    {
        if (this.canSmelt())
        {
        	int resultSlotId = this.slotsinput + 1;
        	List<Item> input = getInputSlots();
        	ItemStack result = new ItemStack(TCrusherBurnRecipies.getResultForItems(input));
        	ItemStack resultSlot = this.furnaceItemStacks.get(resultSlotId);
        	List<Item> output = TCrusherBurnRecipies.getSubsForItems(input);

        	// Основной результат
            if (resultSlot.isEmpty())
            {
                this.furnaceItemStacks.set(resultSlotId, result.copy());
            }
            else if (resultSlot.getItem() == result.getItem())
            {
            	resultSlot.grow(result.getCount());
            }
            
            // Добрасываем суб-продукты в слоты исходящих
            for(int item = 0; item < output.size(); item++){
            	if(output.get(item) == null)
            		continue;
            	for(int slot = resultSlotId + 1; slot < this.slotscount; slot++){
	            	if (this.furnaceItemStacks.get(slot).isEmpty())
	                {
	                    this.furnaceItemStacks.set(slot, new ItemStack(output.get(item)));
	                    break;
	                }
	                else if (this.furnaceItemStacks.get(slot).getItem() == output.get(item))
	                {
	                	this.furnaceItemStacks.get(slot).grow(1);
	                	break;
	                }
            	}
            }

            for(int i = 0; i < this.slotsinput; i++){
            	if(!this.furnaceItemStacks.get(i).isEmpty())
            		this.furnaceItemStacks.get(i).shrink(1);
            }
        }
    }

    // Время пережигания, вынесено в отдельный класс
    public static int getItemBurnTime(ItemStack stack)
    {
        return TCrusherFuel.getFuelTime(stack);
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

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

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

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
            ItemStack itemstack = this.furnaceItemStacks.get(1);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

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

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
            {
                return false;
            }
        }

        return true;
    }

    public String getGuiID()
    {
        return Reference.MOD_ID + ":base_furnace";
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
                return this.furnaceBurnTime;
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

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
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

    public int getFieldCount()
    {
        return 4;
    }

    public void clear()
    {
        this.furnaceItemStacks.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

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

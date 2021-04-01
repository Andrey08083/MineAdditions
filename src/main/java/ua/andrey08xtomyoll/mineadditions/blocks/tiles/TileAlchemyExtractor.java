package ua.andrey08xtomyoll.mineadditions.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.blocks.BlockAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.gui.containers.CustomSlots;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.init.ModSounds;
import ua.andrey08xtomyoll.mineadditions.util.AlchemyExtractorRecipies;
import ua.andrey08xtomyoll.mineadditions.util.TilesFuel;

import javax.annotation.Nullable;

public class TileAlchemyExtractor extends TileEntity implements ITickable, ISidedInventory {
    // Общее кол-во слотов, не менее 3х, не более 11 (5 входящих + топливо + 5 на выходе)
    public static int slotscount = 5;
    // Кол-во входящих (от 1 до 5, с ориентацией на общее кол-во)
    public static int slotsinput = 2;

    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{slotsinput + 1, slotsinput};
    private static final int[] SLOTS_SIDES = new int[]{slotsinput};

    // Все слоты. Сначала входящие, затем топливо, затем результат, затем суб-продукты
    public NonNullList<ItemStack> ExtractorItemStacks = NonNullList.<ItemStack>withSize(slotscount, ItemStack.EMPTY);

    private int ExtratctorBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    public int totalCookTime;
    private String CustomName;
    boolean endburn = false;

    public int getSizeInventory() {
        return this.ExtractorItemStacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.ExtractorItemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getStackInSlot(int index) {
        return this.ExtractorItemStacks.get(index);
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.ExtractorItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.ExtractorItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.ExtractorItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.ExtractorItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index >= 0 && index < this.slotsinput && !flag) {
            this.totalCookTime = AlchemyExtractorRecipies.getCookTimeForItems(getInputSlots());
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName() {
        return this.hasCustomName() ? this.CustomName : "container.alchemy_extractor";
    }

    public boolean hasCustomName() {
        return this.CustomName != null && !this.CustomName.isEmpty();
    }

    public void setCustomInventoryName(String name) {
        this.CustomName = name;
    }

    public static void registerFixesFurnace(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileAlchemyExtractor.class, new String[]{"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.ExtractorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.ExtractorItemStacks);
        this.ExtratctorBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.ExtractorItemStacks.get(1));

        if (compound.hasKey("CustomName", 8)) {
            this.CustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short) this.ExtratctorBurnTime);
        compound.setInteger("CookTime", (short) this.cookTime);
        compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.ExtractorItemStacks);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.CustomName);
        }

        return compound;
    }


    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentTranslation("container.alchemy_extractor");
    }


    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isBurning()
    {
        return this.ExtratctorBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    private boolean isSomethingInput(){
        boolean isSomething = false;
        for(int i = 0; i < this.slotsinput; i++){
            if(!this.ExtractorItemStacks.get(i).isEmpty())
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

    public void update()
    {

        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.ExtratctorBurnTime;
        }

        if (!this.world.isRemote)
        {

            ItemStack fuel = this.ExtractorItemStacks.get(getFuelSlotId());

            if (this.isBurning() || !fuel.isEmpty() && isSomethingInput())
            {

                if (!this.isBurning() && this.canSmelt())
                {
                    this.ExtratctorBurnTime = getItemBurnTime(fuel);
                    this.currentItemBurnTime = this.ExtratctorBurnTime;

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
                                this.ExtractorItemStacks.set(getFuelSlotId(), fuelStack);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {

                    ModMain.log("Процесс плавления: " + this.cookTime + " из " + this.totalCookTime);
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = AlchemyExtractorRecipies.getCookTimeForItems(getInputSlots());
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {

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
                BlockAlchemyExtractor.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }


    private List<Item> getInputSlots(){

        ArrayList<Item> inputItemsStacks = new ArrayList<Item>();
        for(int i = 0; i < slotsinput; i++){
            if(!this.ExtractorItemStacks.get(i).isEmpty())
                inputItemsStacks.add(this.ExtractorItemStacks.get(i).getItem());
        }

        return inputItemsStacks;
    }

    public int getCookTime(NonNullList<Item> stacks)
    {
        return AlchemyExtractorRecipies.getCookTimeForItems(stacks);
    }


    private boolean canSmelt()
    {
        boolean inputChecker = false;

        for(int i = 0; i < this.slotsinput; i++){
            if (!this.ExtractorItemStacks.get(i).isEmpty())
            {
                inputChecker = true;
                break;
            }
        }

        if(!inputChecker)
            return false;

        ItemStack result = new ItemStack(AlchemyExtractorRecipies.getResultForItems(getInputSlots()));

        if (result.isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack resultSlot = this.ExtractorItemStacks.get(this.slotsinput + 1);
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

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            int resultSlotId = this.slotsinput + 1;
            List<Item> input = getInputSlots();
            ItemStack result = new ItemStack(AlchemyExtractorRecipies.getResultForItems(input));
            ItemStack resultSlot = this.ExtractorItemStacks.get(resultSlotId);
            List<Item> output = AlchemyExtractorRecipies.getSubsForItems(input);

            if (resultSlot.isEmpty())
            {
                this.ExtractorItemStacks.set(resultSlotId, result.copy());
            }
            else if (resultSlot.getItem() == result.getItem())
            {
                resultSlot.grow(result.getCount());
            }

            for(int item = 0; item < output.size(); item++){
                if(output.get(item) == null)
                    continue;
                for(int slot = resultSlotId + 1; slot < this.slotscount; slot++){
                    if (endburn = true) {
                        if (this.ExtractorItemStacks.get(slot).isEmpty()) {
                            this.ExtractorItemStacks.set(slot, new ItemStack(output.get(item)));
                            break;
                        } else if (this.ExtractorItemStacks.get(slot).getItem() == output.get(item)) {
                            this.ExtractorItemStacks.get(slot).grow(1);
                            break;
                        }
                    }
                }
            }

            for(int i = 0; i < this.slotsinput; i++){
                if(!this.ExtractorItemStacks.get(i).isEmpty())
                    this.ExtractorItemStacks.get(i).shrink(1);
            }
        }
    }

    public static int getItemBurnTime(ItemStack stack)
    {
        return TilesFuel.getFuelTime(stack);
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
            ItemStack itemstack = this.ExtractorItemStacks.get(1);
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

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerAlchemyExtractor(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.ExtratctorBurnTime;
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
                this.ExtratctorBurnTime = value;
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
        this.ExtractorItemStacks.clear();
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

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {

        super.onDataPacket(networkManager, packet);

        this.handleUpdateTag(packet.getNbtCompound());
    }
}

package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ToolPickaxe extends ItemPickaxe implements IHasModel
{
    public ToolPickaxe(String name, ToolMaterial material)
    {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }


    /*@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        *//*int depth = player.getHeldItem(hand).getTagCompound().getInteger("zone");
        switch (facing) {
            case UP:
            case DOWN:
            case EAST:
                for (int i = 0; i < depth; i++) {
                    BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
                    //super.onBlockStartBreak(player.getHeldItem(hand).getItem().getDefaultInstance(), blockPos, player);
                }
            case WEST:
                for (int i = 0; i < depth; i++) {
                    BlockPos blockPos = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ());
                    //super.onBlockStartBreak(player.getHeldItem(hand).getItem().getDefaultInstance(), blockPos, player);
                }
        }*//*
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }*/

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        NBTTagCompound nbt;
        if (handIn.equals(EnumHand.OFF_HAND)) {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if (playerIn.getHeldItem(handIn).hasTagCompound()) {
            nbt = playerIn.getHeldItem(handIn).getTagCompound();
        }
        else {
            nbt = new NBTTagCompound();
        }
        if (!nbt.hasKey("zone")) {
            nbt.setInteger("zone", 1);
        }
        if (playerIn.isSneaking()) {
            switch (nbt.getInteger("zone")) {
                case 1:
                    nbt.setInteger("zone", 3);
                    break;
                case 3:
                    nbt.setInteger("zone", 5);
                    break;
                case 5:
                    nbt.setInteger("zone", 1);
                    break;
            }
            playerIn.getHeldItem(handIn).setTagCompound(nbt);
            if (worldIn.isRemote)
                playerIn.sendMessage(new TextComponentString("Depth - " + nbt.getInteger("zone")));
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
        else
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

    }

    /*Multitool feature
    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return Sets.newHashSet("pickaxe", "axe", "shovel");
    }*/

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ToolHoe extends ItemHoe implements IHasModel
{
    public ToolHoe(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        ModItems.ITEMS.add(this);
    }

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

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        }
        else {
            nbt = new NBTTagCompound();
        }
        if (!nbt.hasKey("zone")) {
            nbt.setInteger("zone", 1);
        }
        stack.setTagCompound(nbt);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

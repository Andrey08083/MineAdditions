package ua.andrey08xtomyoll.mineadditions.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ToolHoe extends ItemHoe implements IHasModel, IHasEffect {
    public ToolHoe(String name, ToolMaterial material) {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ModMain.creativeTab);
        ModItems.ITEMS.add(this);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("zone", 1);
        stack.setTagCompound(nbt);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        NBTTagCompound nbt = player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound();
        if (player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ToolHoe) {
            return EnumActionResult.FAIL;
        }
        if (player.isSneaking()) {
            if (ConfigHandler.GeneralSettings.depthModesArray.contains(nbt.getInteger("zone"))) {
                nbt.setInteger("zone", ConfigHandler.GeneralSettings.depthModesArray.get((ConfigHandler.GeneralSettings.depthModesArray.indexOf(nbt.getInteger("zone")) + 1) % ConfigHandler.GeneralSettings.depthModesArray.size()));
            } else {
                nbt.setInteger("zone", 1);
            }
            player.getHeldItem(EnumHand.MAIN_HAND).setTagCompound(nbt);
            if (worldIn.isRemote)
                player.sendMessage(new TextComponentString("Depth - " + nbt.getInteger("zone")));
        }
        return EnumActionResult.FAIL;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public void registerModels() {
        ModMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

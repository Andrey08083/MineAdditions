package ua.andrey08xtomyoll.mineadditions.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolHoe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

@Mod.EventBusSubscriber
public class ItemEventHandler {

    @SubscribeEvent
    public static void onArmorEquipped(LivingEvent.LivingUpdateEvent event) {
        Entity player = event.getEntity();
        if (player instanceof EntityPlayer) {
            if ((ModItems.LABATIUM_CHESTPLATE.hasFlyMode && !((EntityPlayer) player).inventory.armorItemInSlot(2).getUnlocalizedName().equals(ModItems.LABATIUM_CHESTPLATE.getUnlocalizedName()))) {
                ((EntityPlayer) player).capabilities.allowFlying = false;
                ((EntityPlayer) player).capabilities.isFlying = false;
                ModItems.LABATIUM_CHESTPLATE.hasFlyMode = false;
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer().isSneaking())
            return;

        if (event.getItemStack().getUnlocalizedName().equals(ModItems.LABATIUM_HOE.getUnlocalizedName())) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (event.getItemStack().hasTagCompound()) {
                nbt = event.getItemStack().getTagCompound();
            }
            if (!nbt.hasKey("zone")) {
                nbt.setInteger("zone", 1);
                event.getItemStack().setTagCompound(nbt);
            }
            Vec3d vec3d = event.getEntityPlayer().getPositionEyes(1);
            Vec3d vec3d1 = event.getEntityPlayer().getLook(1);
            Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 5, vec3d1.y * 5, vec3d1.z * 5);
            RayTraceResult lookingAt = event.getWorld().rayTraceBlocks(vec3d, vec3d2, false, false, true);

            EnumFacing facing = event.getEntityPlayer().getHorizontalFacing();
            BlockPos pos = lookingAt.getBlockPos();

            int depth = event.getItemStack().getTagCompound().getInteger("zone");
            switch (facing) {
                case EAST:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
                        if (event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS_PATH) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.DIRT)) {
                            event.getWorld().setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                        }
                    }
                    break;
                case WEST:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ());
                        if (event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS_PATH) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.DIRT)) {
                            event.getWorld().setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                        }
                    }
                    break;
                case NORTH:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - i);
                        if (event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS_PATH) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.DIRT)) {
                            event.getWorld().setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                        }
                    }
                    break;
                case SOUTH:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + i);
                        if (event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.GRASS_PATH) || event.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.DIRT)) {
                            event.getWorld().setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                        }
                    }
                    break;
            }
        }
    }


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getUnlocalizedName().equals(ModItems.LABATIUM_PICKAXE.getUnlocalizedName())) {
            BlockPos pos = event.getPos();
            EnumFacing facing = event.getPlayer().getHorizontalFacing();
            int depth = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getInteger("zone");
            switch (facing) {
                case EAST:
                    for (int i = 1; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
                        if (event.getWorld().getBlockState(blockPos).getBlockHardness(event.getWorld(), blockPos) > 0) {
                            event.getWorld().destroyBlock(blockPos, true);
                        }
                    }
                    break;
                case WEST:
                    for (int i = 1; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ());
                        if (event.getWorld().getBlockState(blockPos).getBlockHardness(event.getWorld(), blockPos) > 0) {
                            event.getWorld().destroyBlock(blockPos, true);
                        }
                    }
                    break;
                case NORTH:
                    for (int i = 1; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - i);
                        if (event.getWorld().getBlockState(blockPos).getBlockHardness(event.getWorld(), blockPos) > 0) {
                            event.getWorld().destroyBlock(blockPos, true);
                        }
                    }
                    break;
                case SOUTH:
                    for (int i = 1; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + i);
                        if (event.getWorld().getBlockState(blockPos).getBlockHardness(event.getWorld(), blockPos) > 0) {
                            event.getWorld().destroyBlock(blockPos, true);
                        }
                    }
                    break;
            }
        }
    }
}
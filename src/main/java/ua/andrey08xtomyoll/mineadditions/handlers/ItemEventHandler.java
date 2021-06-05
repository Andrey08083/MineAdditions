package ua.andrey08xtomyoll.mineadditions.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.armor.ChestplateBase;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolHoe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSpade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Клас подій, які зв'язані з предметами
 */
@Mod.EventBusSubscriber
public class ItemEventHandler {

    /**
     * Подія, яка виконується кожний ігровий тік (20 разів за секунду) і перевіряє наявність броні на гравці
     *
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onArmorEquipped(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase player = event.getEntityLiving();
        if (player instanceof EntityPlayer) {
            if ((!(PlayerUtils.getPlayerArmorInventory(player, 2) instanceof ChestplateBase)) && ModItems.LABATIUM_CHESTPLATE.isEquipped) {
                ((EntityPlayer) player).capabilities.isFlying = false;
                ((EntityPlayer) player).capabilities.allowFlying = false;
                ModItems.LABATIUM_CHESTPLATE.isEquipped = false;
            }
        }
    }

    /**
     * Метод, який виконується кожен раз, коли гравець отримує пошкодження
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onPlayerBurn(LivingHurtEvent event) {
        EntityLivingBase player = event.getEntityLiving();
        if (player instanceof EntityPlayer) {
            if (PlayerUtils.isAllArmorEqual(player)) {
                if (event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) {
                    event.setAmount(0);
                    player.extinguish();
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * Подія, яка відбувається кожен раз, коли гравець крафтить предмет
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() instanceof ToolPickaxe || event.crafting.getItem() instanceof ToolHoe || event.crafting.getItem() instanceof ToolSpade) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("zone", 1);
            event.crafting.setTagCompound(nbt);
        }
    }

    /**
     * Подія, яка відбувається при натисканні правої кнопи миші
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer().isSneaking())
            return;

        if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ToolHoe) {
            NBTTagCompound nbt = new NBTTagCompound();
            List<BlockPos> blocks = new ArrayList<>();
            if (event.getItemStack().hasTagCompound()) {
                nbt = event.getItemStack().getTagCompound();
            }
            if (!nbt.hasKey("zone")) {
                nbt.setInteger("zone", 1);
                event.getItemStack().setTagCompound(nbt);
            }
            Vec3d vec3d = event.getEntityPlayer().getPositionEyes(1);
            Vec3d vec3d1 = event.getEntityPlayer().getLook(1);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * 5, vec3d1.y * 5, vec3d1.z * 5);
            RayTraceResult lookingAt = event.getWorld().rayTraceBlocks(vec3d, vec3d2, false, false, true);

            EnumFacing facing = event.getEntityPlayer().getHorizontalFacing();
            BlockPos pos = lookingAt.getBlockPos();

            int depth = event.getItemStack().getTagCompound().getInteger("zone");
            switch (facing) {
                case EAST:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
                        blocks.add(blockPos);
                    }
                    break;
                case WEST:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ());
                        blocks.add(blockPos);
                    }
                    break;
                case NORTH:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - i);
                        blocks.add(blockPos);
                    }
                    break;
                case SOUTH:
                    for (int i = 0; i < depth; i++) {
                        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + i);
                        blocks.add(blockPos);
                    }
                    break;
            }

            for (BlockPos block : blocks) {
                if (event.getWorld().getBlockState(block).getBlock().equals(Blocks.GRASS) || event.getWorld().getBlockState(block).getBlock().equals(Blocks.GRASS_PATH) || event.getWorld().getBlockState(block).getBlock().equals(Blocks.DIRT)) {
                    event.getWorld().setBlockState(block, Blocks.FARMLAND.getDefaultState());
                    event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).damageItem(1, event.getEntityPlayer());
                }
            }
        }
    }

    /**
     * Подія, яка відбувається при ламанні блоку
     * @param event параметр події
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if ((event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ToolPickaxe || event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ToolSpade) && event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).hasTagCompound()) {
            BlockPos pos = event.getPos();
            List<BlockPos> blocks = new ArrayList<>();
            EnumFacing facing = event.getPlayer().getHorizontalFacing();
            if (event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).hasTagCompound()) {
                int depth = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getInteger("zone");
                if (depth == 1)
                    return;
                switch (facing) {
                    case EAST:
                        for (int i = 0; i < depth; i++) {
                            BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
                            blocks.add(blockPos);
                        }
                        break;
                    case WEST:
                        for (int i = 0; i < depth; i++) {
                            BlockPos blockPos = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ());
                            blocks.add(blockPos);
                        }
                        break;
                    case NORTH:
                        for (int i = 0; i < depth; i++) {
                            BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - i);
                            blocks.add(blockPos);
                        }
                        break;
                    case SOUTH:
                        for (int i = 0; i < depth; i++) {
                            BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + i);
                            blocks.add(blockPos);
                        }
                        break;
                }
                List<String> toolClasses = new ArrayList<>(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem().getToolClasses(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND)));
                for (BlockPos block : blocks) {
                    if (event.getWorld().getBlockState(block).getBlockHardness(event.getWorld(), block) > 0) {
                        for (String c : toolClasses) {
                            if (event.getWorld().getBlockState(block).getBlock().isToolEffective(c, event.getWorld().getBlockState(block))) {
                                event.getWorld().destroyBlock(block, true);
                                event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).damageItem(1, event.getPlayer());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
     static class PlayerUtils {
        public static Item getPlayerArmorInventory(EntityLivingBase player, int slotIndex) {
            List<ItemStack> list = new ArrayList<>();
            for (ItemStack itemStack : player.getArmorInventoryList()) {
                list.add(itemStack);
            }
            return list.get(slotIndex).getItem();
        }

        public static boolean isAllArmorEqual(EntityLivingBase player) {
            for (ItemStack itemStack : player.getArmorInventoryList()) {
                if (!(itemStack.getItem() instanceof ArmorBase))
                    return false;
            }
            return true;
        }
    }
}
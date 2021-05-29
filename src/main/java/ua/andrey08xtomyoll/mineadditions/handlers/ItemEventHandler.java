package ua.andrey08xtomyoll.mineadditions.handlers;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
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
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.Sys;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.items.armor.ArmorBase;
import ua.andrey08xtomyoll.mineadditions.items.armor.ChestplateBase;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolAxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolHoe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolPickaxe;
import ua.andrey08xtomyoll.mineadditions.items.tools.ToolSpade;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemEventHandler {

    @SubscribeEvent
    public static void onArmorEquipped(LivingEvent.LivingUpdateEvent event) {
        Entity player = event.getEntity();
        if (player instanceof EntityPlayer) {
            if ((!(((EntityPlayer) player).inventory.armorItemInSlot(2).getItem() instanceof ChestplateBase))) {
                ((EntityPlayer) player).capabilities.isFlying = false;
                ((EntityPlayer) player).capabilities.allowFlying = false;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBurn(LivingHurtEvent event) {
        Entity player = event.getEntity();
        if (player instanceof EntityPlayer) {
            if ((((EntityPlayer) player).inventory.armorItemInSlot(2).getItem() instanceof ArmorBase)) {
                if (event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) {
                    event.setAmount(0);
                    player.extinguish();
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() instanceof ToolPickaxe || event.crafting.getItem() instanceof ToolHoe || event.crafting.getItem() instanceof ToolSpade) {
            ItemStack item = event.crafting;
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("zone", 1);
            item.setTagCompound(nbt);
        }
    }

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
}
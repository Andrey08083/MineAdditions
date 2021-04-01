package ua.andrey08xtomyoll.mineadditions.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class TilesFuel {


    // Стандартный метод печи
    public static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;

        } else {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);

            if (burnTime >= 0)

                return burnTime;

            Item item = stack.getItem();

            if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB)) {
                return 75;
            } else if (item == Item.getItemFromBlock(Blocks.WOOL)) {
                return 50;
            } else if (item == Item.getItemFromBlock(Blocks.CARPET)) {
                return 33;
            } else if (item == Item.getItemFromBlock(Blocks.LADDER)) {
                return 150;
            } else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON)) {
                return 50;
            } else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD) {
                return 150;
            } else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
                return 8000;
            } else if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName())) {
                return 100;
            } else if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName())) {
                return 100;
            } else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName())) {
                return 100;
            } else if (item == Items.STICK) {
                return 50;
            } else if (item != Items.BOW && item != Items.FISHING_ROD) {
                if (item == Items.SIGN) {
                    return 100;
                } else if (item == Items.COAL) {
                    return 800;
                } else if (item == Items.LAVA_BUCKET) {
                    return 10000;
                } else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL) {
                    if (item == Items.BLAZE_ROD) {
                        return 1200;
                    } else if (item instanceof ItemDoor && item != Items.IRON_DOOR) {
                        return 100;
                    } else {
                        return item instanceof ItemBoat ? 200 : 0;
                    }

                } else {
                    return 50;
                }
            } else {
                return 150;
            }
        }
    }

    public static int getFuelTime(ItemStack stack) {
        return getFuelTime(stack, 0);
    }

    public static int getFuelTime(ItemStack stack, int type) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            if(type == 0){
                if(item == new ItemStack(Items.POTIONITEM,(int) 1).getItem())
                    return 500;
                return 0;
            }
            else{
                return 0;
            }
        }
    }
}


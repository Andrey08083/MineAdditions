package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

public class MazuriumFruitCrop extends LabatiumFruitCrop
{
    public MazuriumFruitCrop(String name)
    {
        super(name);
        setCreativeTab(ModMain.creativeTab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(this.isMaxAge(state))
            {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.MAZURIUM_FRUIT, 1)));
                worldIn.setBlockState(pos, withAge(0));
                return true;
            }
        }

        return false;
    }

    @Override
    protected Item getCrop()
    {
        return ModItems.MAZURIUM_FRUIT;
    }
}

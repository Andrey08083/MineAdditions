package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

/**
 * Клас Томіумового фрукту
 */
public class TomiumFruitCrop extends LabatiumFruitCrop
{
    /**
     * Конструктор Томіумового фрукту
     * @param name реєстраційне ім'я
     */
    public TomiumFruitCrop(String name)
    {
        super(name);
        setCreativeTab(null);
    }

    /**
     * Метод взаємодії з блоком рослини
     * При взаємодії на останній стадії росту, можна отримати фрукт з рослини
     * @return true, якщо це остання стадія росту, або false, якщо не остання стадія росту
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(this.isMaxAge(state))
            {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.TOMIUM_FRUIT, 1)));
                worldIn.setBlockState(pos, withAge(0));
                return true;
            }
        }

        return false;
    }

    /**
     * Геттер врожаю
     * @return Томіумовий фрукт
     */
    @Override
    protected Item getCrop()
    {
        return ModItems.TOMIUM_FRUIT;
    }
}

package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас Лабатіумового фрукту
 */
public class LabatiumFruitCrop extends BlockCrops implements IHasModel
{

    private static final AxisAlignedBB[] LABATIUM_FRUIT = new AxisAlignedBB[]
            {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    /**
     * Конструктор класу фрукту
     * @param name реєстраційне ім'я
     */
    public LabatiumFruitCrop(String name)
    {
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(null);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
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
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.LABATIUM_FRUIT, 1)));
                worldIn.setBlockState(pos, withAge(0));
                return true;
            }
        }

        return false;
    }

    /**
     * Геттер зерна з рослини
     * @return null, так як було змінено механіку отримання зерна
     */
    @Override
    protected Item getSeed()
    {
        return ModItems.LABATIUM_FRUIT_SEED;
    }

    /**
     * Геттер врожаю
     * @return Лабатіумовий фрукт
     */
    @Override
    protected Item getCrop()
    {
        return ModItems.LABATIUM_FRUIT;
    }

    /**
     * Геттер розміру рамки блоку
     * @param state блок, на який наведено
     * @return розміри з масиву розмірів рамки
     */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return LABATIUM_FRUIT[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }

    /**
     * Геттер зміни стадії росту від взаємодії з кістковим борошном
     * @param world світ, в якому проходить взаємодія
     * @return збільшення на одну стадію росту (1)
     */
    @Override
    protected int getBonemealAgeIncrease(World world)
    {
        return 1;
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}

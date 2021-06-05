package ua.andrey08xtomyoll.mineadditions.items.seeds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

/**
 * Клас-конструктор Лабатіумового зерна
 */
public class LabatiumFruitSeed extends Item implements IHasModel, IPlantable
{
    /**
     * Конструктор Лабатіумового зерна
     */
    public LabatiumFruitSeed(String name)
    {
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(ModMain.creativeTab);

        ModItems.ITEMS.add(this);
    }

    /**
     * Подія, яка виконується при використанні предмету
     * @return результат події
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack)
                && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this)
                && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), ModBlocks.LABATIUM_FRUIT_CROP.getDefaultState());
            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else return EnumActionResult.FAIL;
    }

    /**
     * Геттер типу рослини
     * @param world світ
     * @param pos позиція
     * @return тип рослини
     */
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    /**
     * Геттер типу вже посадженої рослини
     * @param world світ
     * @param pos позиція
     * @return тип рослини
     */
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return ModBlocks.LABATIUM_FRUIT_CROP.getDefaultState();
    }

    @Override
    public void registerModels()
    {
        ModMain.proxy .registerItemRenderer(this, 0, "inventory");
    }
}

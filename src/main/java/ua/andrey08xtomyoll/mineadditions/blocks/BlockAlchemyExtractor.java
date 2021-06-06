package ua.andrey08xtomyoll.mineadditions.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.tiles.TileAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.handlers.GuiHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

import java.util.Random;

/**
 * Кллас блоку механізму AlchemyExtractor
 */

public class BlockAlchemyExtractor extends BlockContainer implements IHasModel {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

    /**
     * Конструктор блоку
     * @param name ім'я блоку
     * @param material матеріал
     * @param isBurning чи проводить блок крафт
     */
    public BlockAlchemyExtractor(String name, Material material, boolean isBurning)
    {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(isBurning ? null : ModMain.creativeTab);
        this.setLightLevel(isBurning ? 0.875F : 0.100F);
        setHardness(6.0F);
        setResistance(50.0F);
        setHarvestLevel("pickaxe",3);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    /**
     * Геттер, визначає, які предмети будуть випадати з блока, коли він буде зламаний
     * @param state стан блоку
     * @param rand випадкове значення
     * @param fortune рівень зачарування удачі предмета, яким ламається блок
     * @return
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.ALCHEMY_EXTRACTOR);
    }

    /**
     * Метод визначає поведінку блока, коли він поставлений в світі
     * @param worldIn світ
     * @param pos позиція
     * @param state стан блоку
     */
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    /**
     * Метод визначає стандартний стан блока
     * @param worldIn світ
     * @param pos позиція
     * @param state стан блоку
     */
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    /**
     * Метод виконується завжди, з короткими випадковоми інтервалами.
     * Використовується для малювання частинок диму та бульбашок
     * @param stateIn стан блоку
     * @param worldIn світ
     * @param pos позиція
     * @param rand випадкове значення
     */
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if (this.isBurning)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            int i = x;
            int j = y;
            int k = z;
            if (rand.nextDouble() < 0.1D)
            {
                worldIn.playSound((double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            for (int l = 0; l < 1; ++l) {
                double d0 =  ((float) i + 0.5) + (rand.nextFloat() - 0.5) * 0.0999999985098839D;
                double d1 =  ((float) j + 0.7) + (rand.nextFloat() - 0.5) * 0.0999999985098839D + 0.5;
                double d2 =  ((float) k + 0.5) + (rand.nextFloat() - 0.5) * 0.0999999985098839D;
                worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0, d1, d2, 0, 0, 0);
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0, 0, 0);
            }

        }
    }

    /**
     * Викликається, коли гравець натиснув ПКМ по блоку
     * @param worldIn світ
     * @param pos позиція
     * @param state стан блоку
     * @param playerIn гравець
     * @param hand рука гравця
     * @param facing сторона блоку
     * @param hitX Х координата, точки на яку натиснув гравець
     * @param hitY Y координата, точки на яку натиснув гравець
     * @param hitZ Z координата, точки на яку натиснув гравець
     * @return чи може грацевь взаємодыяти з блоком
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(ModMain.instance, GuiHandler.GUI_ALCHEMY_EXTRACTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    /**
     * Створення тайла для блока
     * @param worldIn світ
     * @param meta метаданні тайла
     * @return тайл
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAlchemyExtractor();
    }

    /**
     * Змінює блок на його працюючу або не працюючу версію.
     * Викликається в тайлі при крафті предметів
     * @param active чи проводиться крафт
     * @param worldIn світ
     * @param pos позиція
     */
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
            worldIn.setBlockState(pos, ModBlocks.ALCHEMY_EXTRACTOR_ON.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, ModBlocks.ALCHEMY_EXTRACTOR_ON.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);

        }
        else
        {
            worldIn.setBlockState(pos, ModBlocks.ALCHEMY_EXTRACTOR.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, ModBlocks.ALCHEMY_EXTRACTOR.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    /**
     * Метод визачає, який стан потрібно призачити блоку при його втсановленні
     * @param worldIn світ
     * @param pos позиція
     * @param facing сторона блока
     * @param hitX на скільки потрібно розвернути блок по Х
     * @param hitY на скільки потрібно розвернути блок по Y
     * @param hitZ на скільки потрібно розвернути блок по Z
     * @param meta метаданні
     * @param placer сутність, яка встановила блок
     * @return стан блоку
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Прив'язує тайл до блоку
     * @param worldIn світ
     * @param pos позиція
     * @param state стан
     * @param placer сутність, яка встановила блок
     * @param stack стек
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileAlchemyExtractor)
            {
                ((TileAlchemyExtractor)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    /**
     * Викликається, коли цей блок був замінений іншим
     * @param worldIn світ
     * @param pos позиція
     * @param state стан
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileAlchemyExtractor)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileAlchemyExtractor)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    /**
     * Визначає, який предмет буде отримано, коли гравець бере блок з креативу за допомогою середньої кнопки маші
     * @param worldIn
     * @param pos
     * @param state
     * @return
     */
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.ALCHEMY_EXTRACTOR);
    }

    /**
     * Тип рендера для блока
     * @param state стан
     * @return модельний тип рандера
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Конвертує метаданні в стан блока
     * @param meta метаданні
     * @return стан блока
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Конвертує стан блока в метаданні
     * @param state стан
     * @return метаданні
     */
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getIndex();
    }

    /**
     * Повертає стан блоку із заданим обертанням із переданого стану блоку.
     * @param state стан
     * @param rot кут обертання
     * @return стан блоку
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Визачає, як потрібно повернути блок для отримання дзеркальної сторони
     * @param state стан
     * @param mirrorIn дзеркало
     * @return сторона блока
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    /*public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }*/

    /**
     * Чи цей блок непрозорий
     * @param state стан блока
     * @return блок не прозорий
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Чи це повний куб
     * @param state стан блоку
     * @return true
     */
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return true;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    /**
     * Реєстрація моделі предмета блкоа
     */
    @Override
    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}

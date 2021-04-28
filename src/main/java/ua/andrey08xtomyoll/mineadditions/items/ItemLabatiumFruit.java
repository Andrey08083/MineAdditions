package ua.andrey08xtomyoll.mineadditions.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ItemLabatiumFruit extends ItemFood implements IHasModel
{
    public ItemLabatiumFruit(String name, int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(CreativeTabs.FOOD);

        ModItems.ITEMS.add(this);
    }


    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 2));
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 1));
    }


    public EnumAction getItemUseAction (ItemStack stack)
    {
        return EnumAction.EAT;
    }

    public void registerModels()
    {
        ModMain.proxy.registerItemRenderer(this,0,"inventory");
    }
}

package ua.andrey08xtomyoll.mineadditions.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

public class ItemTomiumFruit extends ItemFood  implements IHasModel {

    public ItemTomiumFruit(String name, int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(ModMain.creativeTab);

        ModItems.ITEMS.add(this);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 2));
        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 600, 1));
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

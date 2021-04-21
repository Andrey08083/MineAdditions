package ua.andrey08xtomyoll.mineadditions.items.records;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class UntitledRecord extends RecordBase implements IHasModel {

    public UntitledRecord(String name, SoundEvent soundIn) {
        super(name, soundIn);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(I18n.format("untitled_record.tooltip"));
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){return true;}

}

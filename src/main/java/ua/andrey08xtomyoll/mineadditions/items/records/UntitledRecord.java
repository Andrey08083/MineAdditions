package ua.andrey08xtomyoll.mineadditions.items.records;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.util.IHasEffect;
import ua.andrey08xtomyoll.mineadditions.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Клас загадкової платівки
 */
public class UntitledRecord extends RecordBase implements IHasModel, IHasEffect {

    /**
     * Конструктор загадкової платівки
     * @param name    реєстраційне ім'я
     * @param soundIn звук, який буде програватись при використанні пластинки
     */
    public UntitledRecord(String name, SoundEvent soundIn) {
        super(name, soundIn);
    }

    /**
     * Метод, який показує додаткову інформацію при наведенні курсору на платівку в інвентарі
     * @param stack   предмет, на який наведено курсор
     * @param worldIn світ, в якому вібулася подія (не обов'язковий параметр)
     * @param tooltip масив додаткової інформації
     * @param flagIn  параметр, який відповідає за тип додаткової інформації
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("untitled_record.tooltip"));
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

}

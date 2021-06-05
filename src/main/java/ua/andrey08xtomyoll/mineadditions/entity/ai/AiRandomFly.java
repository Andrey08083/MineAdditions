package ua.andrey08xtomyoll.mineadditions.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

/**
 * Клас реалізує випадковий напрямок польоту для моба, коли він не слідує за гравцем
 */

public class AiRandomFly extends EntityAIBase {
    private final EntityLiving parentEntity;

    /**
     * Конструктор класу
     * @param ghast моб, для якого встановлено таск
     */
    public AiRandomFly(EntityLiving ghast) {
        this.parentEntity = ghast;
        this.setMutexBits(1);
    }

    /**
     * Чи потрібно розпочати виконання EntityAIBase
     * @return дистанція польоту
     */
    public boolean shouldExecute() {
        EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

        if (!entitymovehelper.isUpdating()) {
            return true;
        } else {
            double d0 = entitymovehelper.getX() - this.parentEntity.posX;
            double d1 = entitymovehelper.getY() - this.parentEntity.posY;
            double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }


    public boolean shouldContinueExecuting() {
        return false;
    }

    /**
     * Старт виконання AI таску
     */
    public void startExecuting() {
        Random random = this.parentEntity.getRNG();
        double d0 = this.parentEntity.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.parentEntity.posY + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.parentEntity.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
}

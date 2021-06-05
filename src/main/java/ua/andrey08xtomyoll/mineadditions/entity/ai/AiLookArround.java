package ua.andrey08xtomyoll.mineadditions.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

/**
 * Клас, який визачає, як моб дивиться навкруги та шукає ворога
 */

public class AiLookArround extends EntityAIBase {
    private final EntityLiving parentEntity;

    /**
     * Конструктор класу
     * @param ghast моб, для якого встановлено таск
     */
    public AiLookArround(EntityLiving ghast) {
        this.parentEntity = ghast;
        this.setMutexBits(2);
    }

    /**
     * Чи потрібно розпочати виконання EntityAIBase
     * @return true
     */
    public boolean shouldExecute() {
        return true;
    }

    /**
     * Постійне виконання AI таска
     */
    public void updateTask() {
        if (this.parentEntity.getAttackTarget() == null) {
            this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float) Math.PI);
            this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
        } else {
            EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

            if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D) {
                double d1 = entitylivingbase.posX - this.parentEntity.posX;
                double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
                this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            }
        }
    }
}
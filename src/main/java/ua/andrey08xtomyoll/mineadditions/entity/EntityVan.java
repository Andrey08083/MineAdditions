package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.entity.ai.AiLookArround;
import ua.andrey08xtomyoll.mineadditions.entity.ai.AiRandomFly;
import ua.andrey08xtomyoll.mineadditions.entity.ai.GhastLikeMoveHelper;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModSounds;

public class EntityVan extends EntityMob implements IRangedAttackMob, IMob {
    private int heightOffsetUpdateTime;
    private final float inAccurate = 0.1F;
    public EntityVan(World world) {
        super(world);
        setSize(1F, 2F);
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new GhastLikeMoveHelper(this);
    }


    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof EntityPotion)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
       this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(5, new AiRandomFly(this));
        this.tasks.addTask(7, new AiLookArround(this));
        this.tasks.addTask(3, new EntityAIAttackRanged(this, 2D, 13, 30.0F));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            this.setAttackTarget(this.world.getNearestAttackablePlayer(this, 22.0D, 22.0D));
            if(this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                this.setDead();
            }}}

    public void attackEntityWithRangedAttack(EntityLivingBase target, float flval) {

        EntityVanShoot van_shoot = new EntityVanShoot(this.world, this);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - van_shoot.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        van_shoot.shoot(d1, d2 +(double)f, d3, 2F, inAccurate);
        this.playSound(ModSounds.van_shoot_sound, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(van_shoot);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
    }

    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
        }
        super.updateAITasks();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.van_ambient_sound;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.van_hurt_sound;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.van_death_sound;
    }


    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
    }

    public float getEyeHeight() {
        return 1.7F;
    }

    public static class EntityVanShoot extends EntitySnowball {
        public EntityLivingBase shootingEntity;
        public int ShootDamage = ConfigHandler.generalSettings.vanDamage;
        public EntityVanShoot(World a) { super(a); }

        public EntityVanShoot(World worldIn, double x, double y, double z) {
            super(worldIn, x, y, z);
        }

        public EntityVanShoot(World worldIn, EntityLivingBase shooter)
        {
            super(worldIn, shooter);
        }

        @Override
        protected void onImpact(RayTraceResult result) {
            if (result.entityHit != null) {
                if (result.entityHit instanceof EntityLivingBase && !(result.entityHit instanceof EntityVan))
                {
                    result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) ShootDamage);
                    EntityLivingBase entity = (EntityLivingBase) result.entityHit;
                    for (int i = 0; i < 16; ++i) {
                        double X = entity.posX + (entity.getRNG().nextDouble() - 0.5D) * 8.0D;
                        double Y = MathHelper.clamp(entity.posY + (double) (entity.getRNG().nextInt(16) - 8), 0.0D, (world.getActualHeight() - 1));
                        double Z = entity.posZ + (entity.getRNG().nextDouble() - 0.5D) * 8.0D;
                        entity.attemptTeleport(X, Y, Z);
                    }
                }
            }
        }
        @Override
        public void onUpdate() {
            if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this))) {
                super.onUpdate();
                    this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, posX + 1D, posY + 1D, posZ + 1D, 0.0D, 0.0D, 0.0D);
                    this.world.spawnParticle(EnumParticleTypes.PORTAL, posX + 2D, posY + 1D, posZ + 3D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}


package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.init.ModSounds;

public class EntityCustomBlaze extends EntityMob implements IRangedAttackMob {
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private float inAccurate = 0.1F;

    public EntityCustomBlaze(World world) {
        super(world);
        setSize(1F, 2F);
        //setNoAI(false);
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new EntityFlyHelper(this);
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
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.5D);

    }

    @Override
    protected void initEntityAI() {
        int MaxAttackTime = 10;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIAttackRanged(this, 1.25D, 40, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.van_ambient_sound;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource p_getHurtSound_1_) {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float flval) {

        EntityArrowCustom entityarrow = new EntityArrowCustom(this.world, this);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - entityarrow.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entityarrow.shoot(d1, d2 +(double)f, d3, 5F, 1.0F);
        this.playSound(ModSounds.fire_shoot_sound, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }

    public float getEyeHeight() {
        return 1.7F;
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
            this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
        }

        EntityLivingBase lvt_1_1_ = this.getAttackTarget();
        if (lvt_1_1_ != null && lvt_1_1_.posY + (double)lvt_1_1_.getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset) {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }


    public static class EntityArrowCustom extends EntitySnowball {
        public EntityLivingBase shootingEntity;
        public int ShootDamage = 1;
        public EntityArrowCustom(World a) { super(a); }

        public EntityArrowCustom(World worldIn, double x, double y, double z) {
            super(worldIn, x, y, z);
        }

        public EntityArrowCustom(World worldIn, EntityLivingBase shooter) {
            super(worldIn, shooter);
        }

        @Override
        protected void onImpact(RayTraceResult result) {
            if (result.entityHit != null) {
                if ((result.entityHit instanceof EntityLiving) && !(result.entityHit instanceof EntityCustomBlaze))
                    ((EntityLiving) result.entityHit).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 200, 3));
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) ShootDamage);
                //result.entityHit.setFire(5);
                if (result.entityHit instanceof EntityPlayer) {
                    EntityPlayer player = Minecraft.getMinecraft().player;
                    player.setFire(5);
                    for (int lvt_11_1_ = 0; lvt_11_1_ < 16; ++lvt_11_1_) {
                        double lvt_12_1_ = player.posX + (player.getRNG().nextDouble() - 0.5D) * 8.0D;
                        double lvt_16_1_ = player.posZ + (player.getRNG().nextDouble() - 0.5D) * 8.0D;
                        player.setPosition(lvt_12_1_, player.posY, lvt_16_1_);
                    }
                }
            }
        }
        @Override
        @SideOnly(Side.CLIENT)
        public void onUpdate() {
            if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this))) {
                super.onUpdate();
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
                    this.world.spawnParticle(EnumParticleTypes.FLAME, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
                    //if model = hight perfomance
                    //this.setFire(1);
            }
        }
    }
}


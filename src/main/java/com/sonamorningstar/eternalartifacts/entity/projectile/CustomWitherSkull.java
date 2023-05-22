package com.sonamorningstar.eternalartifacts.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CustomWitherSkull extends WitherSkull {

    public static float defaultSpeed = 2.0F;
    public float damage = 3.0F;
    public static int duration = 40;

    public final float zRot;

    public CustomWitherSkull(EntityType<? extends WitherSkull> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        zRot = (pLevel.getRandom().nextFloat() - 0.5F) * 50;
    }

    public CustomWitherSkull(Level level, double x, double y, double z) {
        this(EntityType.WITHER_SKULL, level);
        this.setPos(x, y, z);
    }

    public CustomWitherSkull(Level level, LivingEntity living) {
        this(level, living.getX(), 0.7 * living.getEyeY() + 0.3 * living.getY(), living.getZ());
        this.setOwner(living);
        this.shootFromRotation(living, living.xRot, living.yRot, 0.0F, defaultSpeed, 0F);
    }

    public CustomWitherSkull(Level level, LivingEntity living, int damageModifier) {
        this(level, living);
        damage += damageModifier;
    }

    public CustomWitherSkull(Level level, LivingEntity living, int damageModifier, int durationModifier) {
        this(level, living, damageModifier);
        duration += durationModifier;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > duration) { discard(); }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity owner = this.getOwner();
            boolean flag;
            if (owner instanceof LivingEntity livingentity) {
                flag = entity.hurt(DamageSource.MAGIC, damage);
                if (flag) {
                    if (entity.isAlive()) { this.doEnchantDamageEffects(livingentity, entity); }
                    else { livingentity.heal(damage); }
                }
            } else { flag = entity.hurt(DamageSource.MAGIC, damage); }

            if (flag && entity instanceof LivingEntity living) {
                living.addEffect(
                        new MobEffectInstance(MobEffects.WITHER, 200, 1, true, true),
                        this.getEffectSource());
            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.5F, false, Explosion.BlockInteraction.NONE);
            discard();
        }
    }
}

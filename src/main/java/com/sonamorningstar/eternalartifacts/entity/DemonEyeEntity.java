package com.sonamorningstar.eternalartifacts.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Random;

public class DemonEyeEntity extends FlyingMob implements Enemy, IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    public static AttributeSupplier setAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0F)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new AIAttack(this));
        this.goalSelector.addGoal(5, new AIFlyTowardsTarget(this));
        this.goalSelector.addGoal(6, new AIRandomFly(this));
        this.goalSelector.addGoal(7, new AILookAround(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() { super.aiStep(); }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.getController().getCurrentAnimation() == null) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.model.demon_eye"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller",
                0, this::predicate ));
    }

    @Override
    public AnimationFactory getFactory() { return this.factory; }


    // AI
    static class AIFlyTowardsTarget extends Goal {
        private final DemonEyeEntity taskOwner;

        AIFlyTowardsTarget(DemonEyeEntity demonEye) {
            this.taskOwner = demonEye;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() { return taskOwner.getTarget() != null; }

        @Override
        public boolean canContinueToUse() { return false; }

        @Override
        public void start() {
            LivingEntity target = taskOwner.getTarget();
            if (target != null)
                taskOwner.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 5F);
        }
    }

    static class AIAttack extends Goal {
        private final DemonEyeEntity taskOwner;
        private int attackTick = 20;

        AIAttack(DemonEyeEntity taskOwner) { this.taskOwner = taskOwner; }

        @Override
        public boolean canUse() {
            LivingEntity target = taskOwner.getTarget();

            return target != null
                    && target.getBoundingBox().maxY > taskOwner.getBoundingBox().minY
                    && target.getBoundingBox().minY < taskOwner.getBoundingBox().maxY
                    && taskOwner.distanceToSqr(target) <= 4.0D;
        }

        @Override
        public void tick() { if (attackTick > 0) { attackTick--; } }

        @Override
        public void stop() { attackTick = 20; }

        @Override
        public void start() {
            if (taskOwner.getTarget() != null)
                taskOwner.doHurtTarget(taskOwner.getTarget());
            attackTick = 20;
        }
    }

    static class AIRandomFly extends Goal {
        private final DemonEyeEntity parentEntity;

        public AIRandomFly(DemonEyeEntity demonEye) {
            this.parentEntity = demonEye;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.parentEntity.getMoveControl();
            if (!moveControl.hasWanted()) { return true; } else {
                double d0 = moveControl.getWantedX() - this.parentEntity.getX();
                double d1 = moveControl.getWantedY() - this.parentEntity.getY();
                double d2 = moveControl.getWantedZ() - this.parentEntity.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0 || d3 > 3600.0;
            }
        }

        @Override
        public boolean canContinueToUse() { return false; }

        @Override
        public void start() {
            Random random = this.parentEntity.getRandom();
            double d0 = this.parentEntity.getX() + (random.nextFloat() - 0.5F) * 16.0F;
            double d1 = this.parentEntity.getY() + (random.nextFloat() - 0.5F) * 16.0F;
            double d2 = this.parentEntity.getZ() + (random.nextFloat() - 0.5F) * 16.0F;
            this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
        }
    }

    public static class AILookAround extends Goal {
        private final DemonEyeEntity parentEntity;

        public AILookAround(DemonEyeEntity demonEye) {
            this.parentEntity = demonEye;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() { return true; }

        @Override
        public void tick() {
            if (this.parentEntity.getTarget() == null) {
                this.parentEntity.setYRot(-((float) Mth.atan2(this.parentEntity.getDeltaMovement().x(), this.parentEntity.getDeltaMovement().z())) * (180F / (float) Math.PI));
                this.parentEntity.setYBodyRot(this.parentEntity.getYRot());
            } else {
                LivingEntity entitylivingbase = this.parentEntity.getTarget();

                if (entitylivingbase.distanceToSqr(this.parentEntity) < 4096.0D) {
                    double d1 = entitylivingbase.getX() - this.parentEntity.getX();
                    double d2 = entitylivingbase.getZ() - this.parentEntity.getZ();
                    this.parentEntity.setYRot(-((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI));
                    this.parentEntity.setYBodyRot(this.parentEntity.getYRot());
                }
            }
        }
    }
}

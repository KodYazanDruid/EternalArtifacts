package com.sonamorningstar.eternalartifacts.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
    Vec3 moveTargetPoint = Vec3.ZERO;

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new DemonEyeEntity.DemonEyeMoveControl(this);
        this.lookControl = new DemonEyeEntity.DemonEyeLookControl(this);

    }

    public static AttributeSupplier setAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0F)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F).build();
    }

    @Override
    protected BodyRotationControl createBodyControl() { return new DemonEyeEntity.DemonEyeBodyRotationControl(this); }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new AIAttack(this));
        this.goalSelector.addGoal(5, new AIFlyTowardsTarget(this));
        this.goalSelector.addGoal(6, new AIRandomFly(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

        /*
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 20F));
         */
    }

    private void despawnIfPeaceful() {
        if (!level.isClientSide && level.getDifficulty() == Difficulty.PEACEFUL)
            discard();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        despawnIfPeaceful();
    }

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

    class DemonEyeLookControl extends LookControl {
        public DemonEyeLookControl(Mob pMob) { super(pMob); }
        public void tick() { }
    }

    class DemonEyeMoveControl extends MoveControl {
        public DemonEyeMoveControl(Mob pMob) {super(pMob); }
        private float speed = 0.1F;

        public void tick() {
            if (DemonEyeEntity.this.horizontalCollision) {
                DemonEyeEntity.this.setYRot(DemonEyeEntity.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            double d0 = DemonEyeEntity.this.moveTargetPoint.x - DemonEyeEntity.this.getX();
            double d1 = DemonEyeEntity.this.moveTargetPoint.y - DemonEyeEntity.this.getY();
            double d2 = DemonEyeEntity.this.moveTargetPoint.z - DemonEyeEntity.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > (double)1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double)0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = DemonEyeEntity.this.getYRot();
                float f1 = (float)Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(DemonEyeEntity.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180F / (float)Math.PI));
                DemonEyeEntity.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                DemonEyeEntity.this.yBodyRot = DemonEyeEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(f, DemonEyeEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f4 = (float)(-(Mth.atan2(-d1, d3) * (double)(180F / (float)Math.PI)));
                DemonEyeEntity.this.setXRot(f4);
                float f5 = DemonEyeEntity.this.getYRot() + 90.0F;
                double d6 = (double)(this.speed * Mth.cos(f5 * ((float)Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double)(this.speed * Mth.sin(f5 * ((float)Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double)(this.speed * Mth.sin(f4 * ((float)Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = DemonEyeEntity.this.getDeltaMovement();
                DemonEyeEntity.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }

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
            if (taskOwner.getTarget() != null) { taskOwner.doHurtTarget(taskOwner.getTarget()); }
            attackTick = 20;
        }
    }

    static class AIRandomFly extends Goal {
        private final DemonEyeEntity parent;

        public AIRandomFly(DemonEyeEntity mob) {
            this.parent = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.parent.getMoveControl().hasWanted() && this.parent.getRandom().nextFloat() < 0.01F;
        }

        @Override
        public boolean canContinueToUse() { return false; }

        @Override
        public void start() {
            Random random = this.parent.getRandom();
            double d0 = this.parent.getX() + (random.nextFloat() * 2.0F - 1.0F) * 3.0F;
            double d1 = this.parent.getY() + (random.nextFloat() * 2.0F - 1.0F) * 3.0F;
            double d2 = this.parent.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 3.0F;
            this.parent.getMoveControl().setWantedPosition(d0, d1, d2, 0.15D);
        }
    }

    public static class AILookAround extends Goal {
        private final DemonEyeEntity parent;
        private double lookX;
        private double lookZ;
        private int idleTime;

        public AILookAround(DemonEyeEntity mob) {
            this.parent = mob;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() { return this.parent.getRandom().nextFloat() < 0.008F; }

        @Override
        public boolean canContinueToUse() { return this.idleTime >= 0; }

        @Override
        public void start() {
            double d0 = (Math.PI * 2.0D) * this.parent.getRandom().nextDouble();
            this.lookX = Math.cos(d0);
            this.lookZ = Math.sin(d0);
            this.idleTime = 20 + this.parent.getRandom().nextInt(20);
        }

        @Override
        public void tick() {
            --this.idleTime;
            this.parent.setYRot(-((float)Mth.atan2(this.lookX, this.lookZ)) * (180.0F / (float)Math.PI));
            this.parent.yBodyRot = this.parent.getYRot();
        }
    }

    class DemonEyeBodyRotationControl extends BodyRotationControl {
        public DemonEyeBodyRotationControl(Mob pMob) {
            super(pMob);
        }

        public void clientTick() {
            DemonEyeEntity.this.yHeadRot = DemonEyeEntity.this.yBodyRot;
            DemonEyeEntity.this.yBodyRot = DemonEyeEntity.this.getYRot();
        }
    }
}

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
import net.minecraft.world.phys.AABB;
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
        this.lookControl = new DemonEyeEntity.DemonEyeLookControl(this);
        this.moveControl = new DemonEyeEntity.DemonEyeMoveControl(this);
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
        private final DemonEyeEntity demonEyeEntity;
        private int floatDuration;

        public DemonEyeMoveControl(DemonEyeEntity pDemonEyeEntity) {
            super(pDemonEyeEntity);
            this.demonEyeEntity = pDemonEyeEntity;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.demonEyeEntity.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.demonEyeEntity.getX(), this.wantedY - this.demonEyeEntity.getY(), this.wantedZ - this.demonEyeEntity.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.demonEyeEntity.setDeltaMovement(this.demonEyeEntity.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 pPos, int pLength) {
            AABB aabb = this.demonEyeEntity.getBoundingBox();

            for(int i = 1; i < pLength; ++i) {
                aabb = aabb.move(pPos);
                if (!this.demonEyeEntity.level.noCollision(this.demonEyeEntity, aabb)) {
                    return false;
                }
            }

            return true;
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
            if (target != null){
                taskOwner.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 5F);
                taskOwner.getLookControl().setLookAt(target);
            }
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
        private final DemonEyeEntity demoneye;

        public AIRandomFly(DemonEyeEntity pDemonEyeEntity) {
            this.demoneye = pDemonEyeEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            MoveControl moveControl = this.demoneye.getMoveControl();
            LookControl lookControl = this.demoneye.getLookControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double d0 = moveControl.getWantedX() - this.demoneye.getX();
                double d1 = moveControl.getWantedY() - this.demoneye.getY();
                double d2 = moveControl.getWantedZ() - this.demoneye.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                lookControl.setLookAt(moveControl.getWantedX(), moveControl.getWantedY(), moveControl.getWantedZ());
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Random random = this.demoneye.getRandom();
            double d0 = this.demoneye.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.demoneye.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.demoneye.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.demoneye.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
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

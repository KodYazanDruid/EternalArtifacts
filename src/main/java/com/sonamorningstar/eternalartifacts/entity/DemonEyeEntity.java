package com.sonamorningstar.eternalartifacts.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
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
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, false);
        this.lookControl = new DemonEyeEntity.DemonEyeLookControl(this);
    }

    public static AttributeSupplier setAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0F)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.FLYING_SPEED, 3F).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new AIFlyTowardsTarget(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new AIRandomFly(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void despawnIfPeaceful() {
        if (!level.isClientSide && level.getDifficulty() == Difficulty.PEACEFUL)
            discard();
    }

    @Override
    public void playerTouch(Player pPlayer) {
        this.doHurtTarget(pPlayer);
        super.playerTouch(pPlayer);
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

    @Override protected @NotNull BodyRotationControl createBodyControl() { return new DemonEyeEntity.DemonEyeBodyControl(this); }

    @Override
    public AnimationFactory getFactory() { return this.factory; }

    class DemonEyeLookControl extends LookControl {
        public DemonEyeLookControl(Mob pMob) {
            super(pMob);
        }

        /**
         * Updates look
         */
        public void tick() {
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
                taskOwner.getMoveControl().setWantedPosition(target.getX(), target.getY() + 1, target.getZ(), taskOwner.getAttributeValue(Attributes.FLYING_SPEED));
            }
        }
    }

    static class AIRandomFly extends Goal {
        private final DemonEyeEntity taskOwner;

        public AIRandomFly(DemonEyeEntity demonEye) {
            this.taskOwner = demonEye;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return taskOwner.navigation.isDone();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() { return taskOwner.navigation.isInProgress(); }


        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Random random = taskOwner.getRandom();
            double d0 = taskOwner.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = taskOwner.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = taskOwner.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            taskOwner.getMoveControl().setWantedPosition(d0, d1, d2, taskOwner.getAttributeValue(Attributes.FLYING_SPEED));
        }
    }

    class DemonEyeBodyControl extends BodyRotationControl {
        public DemonEyeBodyControl(Mob pMob) {
            super(pMob);
        }

        /**
         * Update the Head and Body rendenring angles
         */
        public void clientTick() {
            DemonEyeEntity.this.yHeadRot = DemonEyeEntity.this.yBodyRot;
            DemonEyeEntity.this.yBodyRot = DemonEyeEntity.this.getYRot();
        }
    }

}

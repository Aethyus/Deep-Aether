package teamrazor.deepaether.entity.living.boss.eots;

import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DAEntities;

public class EOTSSegment extends FlyingMob implements Enemy {

    private boolean shouldGoToMiddle = false; //Not yet implemented
    private BlockPos middle; //Not yet implemented

    @Nullable
    protected EOTSSegment parent;
    @Nullable
    private UUID parentUUID;

    @Nullable
    protected EOTSController controller; //Not yet implemented
    @Nullable
    private UUID controllerUUID; //Not yet implemented
    private static final EntityDataAccessor<Boolean> DATA_HEAD_ID = SynchedEntityData.defineId(EOTSSegment.class, EntityDataSerializers.BOOLEAN);

    public EOTSSegment(EntityType<? extends EOTSSegment> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new EotsSegmentMoveControl(this);
    }

    /**
     * Used to spawn multiple segments at once
     * {@link EOTSController.SEGMENT_COUNT}
     */
    protected EOTSSegment(Level level, EOTSSegment parent, int length) {
        super(DAEntities.EOTS_SEGMENT.get(), level);
        this.moveControl = new EotsSegmentMoveControl(this);
        this.setPos(parent.getOnPos().getCenter());
        level.addFreshEntity(this);
        this.setParent(parent);
        if (length < EOTSController.SEGMENT_COUNT) {
            new EOTSSegment(level, this, length + 1);
        }
    }

    public EOTSSegment(Level level, EOTSSegment parent, EOTSController controller) {
        super(DAEntities.EOTS_SEGMENT.get(), level);
        this.moveControl = new EotsSegmentMoveControl(this);
        this.setPos(parent.getOnPos().getCenter());
        level.addFreshEntity(this);
        this.setParent(parent);
        this.setController(controller);
    }

    public EOTSSegment(Level level, EOTSController controller) {
        super(DAEntities.EOTS_SEGMENT.get(), level);
        this.moveControl = new EotsSegmentMoveControl(this);
        this.setPos(controller.getOnPos().getCenter());
        level.addFreshEntity(this);
        this.setController(controller);
    }


    /**
     * Max Health should be equal to the controller's health divided by the numbers of segments
     */
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_HEAD_ID, true);
    }

    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        new EOTSSegment(this.level(), this, 0); //Ensures multiple segments spawns in if spawn command is used
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomFloatAroundGoal(this));
    }

    /**
     * Used to rotate and position a body segment.
     * Converts a body segment into a head segment if the parent (head) segment is missing
     */
    public void tick() {
        super.tick();
        if (!this.isControllingSegment()) {
            EOTSSegment parent = this.getParent();
            if (parent != null) {
                float yRot = this.getYRot();
                float yParentRot = parent.getYRot();
                float newYRot;
                if (Math.abs(yRot - yParentRot) < 0.1F) {
                    newYRot = yRot;
                } else {
                    newYRot = Mth.lerp(0.15F, yRot, yParentRot);
                }

                float xRot = this.getXRot();
                float xParentRot = parent.getXRot();
                float newXRot;
                if (Math.abs(xRot - xParentRot) < 0.1F) {
                    newXRot = xRot;
                } else {
                    newXRot = Mth.lerp(0.15F, xRot, xParentRot);
                }

                this.setRot(newYRot, newXRot); //Rotates the segment towards the parent segment
                this.setPos(parent.position().subtract(parent.getLookAngle().multiply(0.6499999761581421, 0.6499999761581421, 0.6499999761581421))); //Positions the segment behind the parent segment
            }
            else if(this.level() instanceof ServerLevel) {
                this.setControllingSegment(true); //Converts a body segment into a head segment
                if(this.getController() != null)
                    this.getController().controllingSegments.add(this); //Adds itself to the list of controlling Segments
            }
        }

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(this.getController() != null) {
            if (pAmount > this.getHealth())
                this.getController().hurt(pSource, this.getHealth());
            else this.getController().hurt(pSource, pAmount);
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if(this.getController() != null) {
            this.getController().segments.remove(this);
            if(this.isControllingSegment())
                this.getController().controllingSegments.add(this);
        }
        super.die(pDamageSource);
    }

    public boolean canCollideWith(@NotNull Entity pEntity) {
        return pEntity.getType() != DAEntities.EOTS_SEGMENT.get();
    }

    /**
     * @return whenever the segment is a head (controlling segment) or not
     * Used in {@link teamrazor.deepaether.client.model.EOTSSegmentModel} to renderer the correct model
     */
    public boolean isControllingSegment() {
        return this.getEntityData().get(DATA_HEAD_ID);
    }

    public void setControllingSegment(boolean head) {
        this.getEntityData().set(DATA_HEAD_ID, head);
    }

    /**
     * @return the parent's UUID as a fallback if {@link parent} is missing
     */

    private @Nullable UUID getParentUUID() {
        return this.parentUUID;
    }

    /**
     * Checks if the parent is present, and uses the parent's UUID as a fallback
     * @return the parent object
     */
    @Nullable
    public EOTSSegment getParent() {
        if (this.parent != null && !this.parent.isRemoved()) {
            return this.parent;
        } else if (this.parent == null && this.level() instanceof ServerLevel) {
            this.parent = (EOTSSegment)((ServerLevel)this.level()).getEntity(this.parentUUID);
            return this.parent;
        } else {
            return null;
        }
    }

    private void setParentUUID(@Nullable UUID uuid) {
        this.parentUUID = uuid;
    }

    /**
     * Saves the parent object and the parent's UUID
     */
    public void setParent(@Nullable EOTSSegment parent) {
        this.setControllingSegment(parent == null);
        this.parent = parent;
        if (parent == null) {
            this.setParentUUID(null);
        } else {
            this.setParentUUID(parent.getUUID());
        }

    }

    private void setControllerUUID(@Nullable UUID uuid) {
        this.controllerUUID = uuid;
    }

    @Nullable
    private UUID getControllerUUID() {
        return this.controllerUUID;
    }

    public void setController(@Nullable EOTSController controller) {
        this.controller = controller;
        if (parent == null) {
            this.setControllerUUID(null);
        } else {
            this.setControllerUUID(parent.getUUID());
        }
    }


    /**
     * Checks if an EOTSController is present, and uses the EOTSController's UUID as a fallback
     * @return the EOTSController object
     */
    @Nullable
    public EOTSController getController() {
        if (this.controller != null && !this.controller.isRemoved()) {
            return this.controller;
        } else if (this.controller == null && this.level() instanceof ServerLevel) {
            this.controller = (EOTSController) ((ServerLevel)this.level()).getEntity(this.getControllerUUID());
            return this.controller;
        } else {
            return null;
        }
    }

    /**
     * Not yet implemented
     * @param middle The position the controlling segment should move towards
     */
    public void setGoToMiddle(BlockPos middle) {
        this.shouldGoToMiddle = true;
        this.middle = middle;
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if(tag.hasUUID("Controller"))
            this.setControllerUUID(tag.getUUID("Controller"));
        if (tag.hasUUID("Parent")) {
            this.setParentUUID(tag.getUUID("Parent"));
            this.setControllingSegment(false);
            if(this.getController() != null)  //Hope I don't call this to early
                this.getController().segments.add(this);
        }
        else if(this.getController() != null) {
            this.getController().controllingSegments.add(this);
            this.getController().segments.add(this);
        }
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if(this.getControllerUUID() != null)
            tag.putUUID("Controller", this.getControllerUUID());
        if (this.getParentUUID() != null) {
            tag.putUUID("Parent", this.getParentUUID());
        }

    }

    /**
     * Modified version of {@link net.minecraft.world.entity.monster.Phantom.PhantomMoveControl}
     * Handles the rotation and movement of the controlling segment
     */

    protected static class EotsSegmentMoveControl extends MoveControl {
        private final EOTSSegment segment;
        private float speed = 0.1F;

        public EotsSegmentMoveControl(EOTSSegment segment) {
            super(segment);
            this.segment = segment;
        }

        public void tick() {
            if (this.segment.isControllingSegment()) {
                double d0 = this.segment.moveControl.getWantedX() - this.segment.getX();
                double d1 = this.segment.moveControl.getWantedY() - this.segment.getY();
                double d2 = this.segment.moveControl.getWantedZ() - this.segment.getZ();
                double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                if (Math.abs(d3) > 9.999999747378752E-6) {
                    double d4 = 1.0 - Math.abs(d1 * 0.699999988079071) / d3;
                    d0 *= d4;
                    d2 *= d4;
                    d3 = Math.sqrt(d0 * d0 + d2 * d2);
                    double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                    float f = this.segment.getYRot();
                    float f1 = (float)Mth.atan2(d2, d0);
                    float f2 = Mth.wrapDegrees(this.segment.getYRot() + 90.0F);
                    float f3 = Mth.wrapDegrees(f1 * 57.295776F);
                    this.segment.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                    this.segment.yBodyRot = this.segment.getYRot();
                    if (Mth.degreesDifferenceAbs(f, this.segment.getYRot()) < 3.0F) {
                        this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                    } else {
                        this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                    }

                    float f4 = (float)(-(Mth.atan2(-d1, d3) * 180.0 / 3.1415927410125732));
                    this.segment.setXRot(f4);
                    float f5 = this.segment.getYRot() + 90.0F;
                    double d6 = (double)(this.speed * Mth.cos(f5 * 0.017453292F)) * Math.abs(d0 / d5);
                    double d7 = (double)(this.speed * Mth.sin(f5 * 0.017453292F)) * Math.abs(d2 / d5);
                    double d8 = (double)(this.speed * Mth.sin(f4 * 0.017453292F)) * Math.abs(d1 / d5);
                    Vec3 vec3 = this.segment.getDeltaMovement();
                    this.segment.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2)));
                }
            }

        }
    }

    /**
     * Makes the segment randomly float around when no other goals are active
     * Should be updated release
     */
    protected static class RandomFloatAroundGoal extends Goal {
        private final EOTSSegment segment;

        public RandomFloatAroundGoal(EOTSSegment segment) {
            this.segment = segment;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!this.segment.isControllingSegment()) {
                return false;
            } else {
                MoveControl moveControl = this.segment.getMoveControl();
                if (!moveControl.hasWanted()) {
                    return true;
                } else {
                    double d0 = moveControl.getWantedX() - this.segment.getX();
                    double d1 = moveControl.getWantedY() - this.segment.getY();
                    double d2 = moveControl.getWantedZ() - this.segment.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    return d3 < 1.0 || d3 > 3600.0;
                }
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource random = this.segment.getRandom();
            double d0 = this.segment.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.segment.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.segment.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.segment.getMoveControl().setWantedPosition(d0, d1, d2, 0.5);
        }
    }
}

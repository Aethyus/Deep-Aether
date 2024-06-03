package teamrazor.deepaether.entity.living.boss.eots;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.entity.ai.controller.BlankMoveControl;
import com.aetherteam.aether.entity.ai.goal.MostDamageTargetGoal;
import com.aetherteam.aether.entity.monster.dungeon.boss.BossNameGenerator;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.network.packet.clientbound.BossInfoPacket;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import teamrazor.deepaether.init.DABlocks;
import teamrazor.deepaether.init.DAEntities;

import java.util.ArrayList;
import java.util.List;

public class EOTSController extends Mob implements GeoEntity, AetherBossMob<EOTSController>, Enemy {
    //protected List<EOTSSegment> segments = new ArrayList<>();
    protected List<EOTSSegment> controllingSegments = new ArrayList<>();
    protected List<EOTSSegment> segments = new ArrayList<>();
    public static final int SEGMENT_COUNT = 9;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(EOTSController.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(EOTSController.class, EntityDataSerializers.COMPONENT);
    private MostDamageTargetGoal mostDamageTargetGoal;
    private final ServerBossEvent bossFight;
    protected @Nullable BossRoomTracker<EOTSController> brassDungeon;

    public EOTSController(EntityType<? extends EOTSController> type, Level level) {
        super(type, level);
        this.moveControl = new BlankMoveControl(this);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.setBossFight(false);
        this.xpReward = 50;
        this.setRot(0.0F, 0.0F);
        this.noPhysics = true;
        this.setPersistenceRequired();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setBossName(generateEOTSName(this.getRandom()));
        this.moveTo(Mth.floor(this.getX()), this.getY(), Mth.floor(this.getZ()));
        return spawnData;
    }

    /**
     * Generates a name for the EOTS boss.
     */
    public static MutableComponent generateEOTSName(RandomSource random) {
        MutableComponent result = BossNameGenerator.generateBossName(random);
        return result.append(Component.translatable("gui.deep_aether.eots.title"));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 400.0F).add(Attributes.FOLLOW_RANGE, 128.0F);
    }

    protected void registerGoals() {
        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(2, new selectControllingSegmentGoal(this));
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_AWAKE_ID, false);
        this.getEntityData().define(DATA_BOSS_NAME_ID, Component.literal("EOTS"));
    }

    public void tick() {
        super.tick();
        if (!this.isAwake() || (this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }

        if(!this.firstTick && this.isAwake() && segments.isEmpty()) {
            this.hurt(this.level().damageSources().mobAttack(this), 400.0F);
        }
    }

    private void evaporate() {
        Pair<BlockPos, BlockPos> minMax = this.getDefaultBounds(this);
        AetherBossMob.super.evaporate(this, minMax.getLeft(), minMax.getRight(), (blockState) -> true);
    }

    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return super.hurt(source, amount);
        else if (!this.isBossFight()) {
            this.start();
            return false;
        }
        if (source.getEntity() != null) {
            if (source.isIndirect()) {
                if (source.getEntity().getType() == DAEntities.EOTS_SEGMENT.get()) {
                    return super.hurt(source, amount);
                }
            }
            else if (source.getEntity().is(this))
                return super.hurt(source, amount);
        }

        else return false;


        return super.hurt(source, amount);
    }

    private void start() {
        if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.getRandom().nextFloat() * 0.2F + 0.9F));
        }

        this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.closeRoom();
        }

        this.spawnSegments();
        this.setInvisible(true);
        AetherEventDispatch.onBossFightStart(this, this.getDungeon());
    }

    @Override
    public void reset() {
        this.setDeltaMovement(Vec3.ZERO);
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
            this.openRoom();
        }

        this.setInvisible(false);
        this.removeAllSegments();
        AetherEventDispatch.onBossFightStop(this, this.getDungeon());
    }

    public void die(DamageSource source) {
        this.setDeltaMovement(Vec3.ZERO);
        if (this.level() instanceof ServerLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(source);
                this.tearDownRoom();
            }
        }

        this.removeAllSegments();
        super.die(source);
    }

    private void spawnSegments() {
        EOTSSegment oldSegment = new EOTSSegment(this.level(), this);
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            oldSegment = new EOTSSegment(this.level(), oldSegment, this);
        }
    }

    public void removeSegment(EOTSSegment segment) {
        this.segments.remove(segment);
    }
    public void setControllingSegment(EOTSSegment segment) {
        this.controllingSegments.add(segment);
    }

    public void removeControllingSegment(EOTSSegment segment) {
        this.controllingSegments.remove(segment);
    }


    private void removeAllSegments() {
        for (EOTSSegment segment : this.segments) {
            segment.remove(RemovalReason.DISCARDED);
        }

    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    @Override
    public void push(double x, double y, double z) {
    }

    @Override
    public void checkDespawn() {
    }

    @Nullable
    @Override
    public BlockState convertBlock(BlockState state) {
        if (state.is(DABlocks.LOCKED_NIMBUS_STONE.get())) {
            return DABlocks.NIMBUS_STONE.get().defaultBlockState();
        } else if (state.is(DABlocks.LOCKED_LIGHT_NIMBUS_STONE.get())) {
            return DABlocks.LIGHT_NIMBUS_STONE.get().defaultBlockState();
        } else if (state.is(DABlocks.BOSS_DOORWAY_NIMBUS_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return state.is(DABlocks.TREASURE_DOORWAY_NIMBUS_STONE.get()) ? AetherBlocks.SKYROOT_TRAPDOOR.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, (Direction)state.getValue(HorizontalDirectionalBlock.FACING)) : null;
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        PacketRelay.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId(), this.getId()), player);
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(player)) {
            this.bossFight.addPlayer(player);
            AetherEventDispatch.onBossFightPlayerAdd(this, this.getDungeon(), player);
        }

    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        PacketRelay.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId(), this.getId()), player);
        this.bossFight.removePlayer(player);
        AetherEventDispatch.onBossFightPlayerRemove(this, this.getDungeon(), player);
    }

    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
            AetherEventDispatch.onBossFightPlayerAdd(this, this.getDungeon(), serverPlayer);
        }

    }

    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
            AetherEventDispatch.onBossFightPlayerRemove(this, this.getDungeon(), serverPlayer);
        }

    }

    public boolean isAwake() {
        return this.getEntityData().get(DATA_AWAKE_ID);
    }

    public void setAwake(boolean awake) {
        this.getEntityData().set(DATA_AWAKE_ID, awake);
    }

    public Component getBossName() {
        return this.getEntityData().get(DATA_BOSS_NAME_ID);
    }

    public void setBossName(Component component) {
        this.getEntityData().set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    @Nullable
    @Override
    public BossRoomTracker<EOTSController> getDungeon() {
        return this.brassDungeon;
    }

    @Override
    public void setDungeon(@Nullable BossRoomTracker<EOTSController> dungeon) {
        this.brassDungeon = dungeon;
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }


    /**
     * Temporary
     * @return The {@link ResourceLocation} for this boss's health bar.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarTexture() {
        return new ResourceLocation(Aether.MODID, "boss_bar/slider");
    }

    /**
     * Temporary
     * @return The {@link ResourceLocation} for this boss's health bar background.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarBackgroundTexture() {
        return new ResourceLocation(Aether.MODID, "boss_bar/slider_background");
    }
    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.setBossName(name);
    }

    protected SoundEvent getAwakenSound() {
        return  AetherSoundEvents.ENTITY_SLIDER_AWAKEN.get();
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SLIDER_DEATH.get();
    }

    @Override
    @NotNull
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return false;
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public float getYRot() {
        return 0.0F;
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldDiscardFriction() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isFullyFrozen() {
        return false;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addBossSaveData(tag);
        tag.putBoolean("Awake", this.isAwake());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        if (tag.contains("Awake")) {
            this.setAwake(tag.getBoolean("Awake"));
            this.setInvisible(true);
        }

    }

    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addBossSaveData(tag);
        buffer.writeNbt(tag);
    }

    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        if (tag != null) {
            this.readBossSaveData(tag);
        }

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    // Animation handling
    private PlayState predicate(AnimationState animationState) {
        if(this.isAutoSpinAttack()) {
            animationState.getController().setAnimation(RawAnimation.begin().thenPlay("animation.eots.attack"));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public static class selectControllingSegmentGoal extends Goal {
        int timer = 200;
        EOTSController controller;

        private selectControllingSegmentGoal(EOTSController controller) {
            this.controller = controller;
        }

        @Override
        public boolean canUse() {
            if(this.controller.controllingSegments.isEmpty())
                return false;
            if (this.timer <= 0) {
                return true;
            } else {
                --this.timer;
                return false;
            }
        }

        @Override
        public void start() {
            this.timer = 200;
            this.controller.controllingSegments.get(this.controller.level().getRandom().nextInt(this.controller.controllingSegments.size())).setGoToMiddle(this.controller.blockPosition().above(2));
        }
    }
}
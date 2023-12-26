package teamrazor.deepaether.event;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import teamrazor.deepaether.DeepAetherMod;
import teamrazor.deepaether.entity.IFlawlessBossDrop;
import teamrazor.deepaether.entity.IPlayerBossFight;
import teamrazor.deepaether.entity.MoaBonusJump;
import teamrazor.deepaether.init.DAItems;
import teamrazor.deepaether.init.DAMobEffects;

@Mod.EventBusSubscriber(modid = DeepAetherMod.MODID)
public class DAGeneralEvents {

    /**
     * Used to check if a player has been hurt during a boss fight
     * See {@link IPlayerBossFight}
     * See {@link IFlawlessBossDrop}
     */
    @SubscribeEvent
    public static void onLivingEntityHurt(LivingHurtEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            //Gets the stored boss from the player
            Entity playerBossFight = ((IPlayerBossFight) player).deep_Aether$getBoss();

            //checks if the stored boss has a flawless boss drop
            if (playerBossFight instanceof IFlawlessBossDrop flawless) {

                //Sets the deep_Aether$setHasBeenHurt to true. The flawless boss drop will not drop when the boss dies.
                flawless.deep_Aether$setHasBeenHurt(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == AetherEntityTypes.SLIDER.get() && DeepAetherMod.IsHalloweenContentEnabled()) {
            entity.spawnAtLocation(new ItemStack(DAItems.SPOOKY_RING.get(), 1));
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event)
    {
        var blocker = event.getEntity();
        DamageSource source = event.getDamageSource();
        if(ModList.get().isLoaded(DeepAetherMod.LOST_AETHER_CONTENT)) {
            if (blocker.getUseItem().is(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(DeepAetherMod.LOST_AETHER_CONTENT, "aether_shields")))) {
                blocker.getLevel().playSound(null, blocker.blockPosition(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, blocker.getSoundSource(), 0.4F, 0.8F + blocker.getLevel().random.nextFloat() * 0.4F);

                if (blocker.getUseItem().getItem() == DAItems.STRATUS_SHIELD.get() && source.getDirectEntity() instanceof LivingEntity attacker) {
                    attacker.knockback(1.5F, blocker.getX() - attacker.getX(), blocker.getZ() - attacker.getZ());
                    attacker.setPos(attacker.getX(), attacker.getY() + 1D, attacker.getZ());
                    attacker.hasImpulse = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove effectEvent) {
        LivingEntity entity = effectEvent.getEntity();
        MobEffect effect = effectEvent.getEffect();
        if(entity instanceof Moa moa && effect.equals(DAMobEffects.MOA_BONUS_JUMPS.get())) {
            MoaBonusJump moaBonusJump = (MoaBonusJump) moa;
            moaBonusJump.deep_Aether$setBonusJumps(0);
        }
    }
}

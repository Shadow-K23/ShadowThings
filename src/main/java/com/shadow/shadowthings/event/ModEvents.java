package com.shadow.shadowthings.event;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.effect.ModEffects;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.item.custom.HammerItem;
import com.shadow.shadowthings.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ShadowThings.MODID)
public class ModEvents {
    private  static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);

            }
        }
    }
    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event){
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player){
            if(player.getMainHandItem().getItem() == Items.END_ROD){
                player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a sheep using an END ROD?"));
                sheep.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200,10));
                player.getMainHandItem().shrink(1);
            }

        }
    }
    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
        builder.addMix(Potions.AWKWARD, ModItems.SHADOW_BERRIES.get(), ModPotions.SHADOW_CURSE_POTION);
    }

    //SHADOW CURSE
    @SubscribeEvent
    public static void onEntityTakeDamage(LivingDamageEvent.Post event){
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.SHADOW_CURSE_EFFECT)){
            if (event.getSource().is(DamageTypes.MAGIC)) {return;}
                float damageAmount = event.getNewDamage();
                if (damageAmount > 0 && event.getSource().getEntity() instanceof LivingEntity attacker) {

                    MobEffectInstance effectInstance = entity.getEffect(ModEffects.SHADOW_CURSE_EFFECT);
                    int effectLevel = effectInstance.getAmplifier() + 1;
                    float damagePercent = 0.10f * effectLevel;
                    float damageBack = damagePercent * damageAmount;
                    entity.invulnerableTime = 0;
                    entity.hurt(attacker.damageSources().magic(), damageBack);
                    entity.level().playSound(
                            null,
                            entity.getX(), entity.getY(), entity.getZ(),
                            SoundEvents.ALLAY_HURT, // Replace with any sound you want!
                            SoundSource.PLAYERS,
                            0.35f, 0.1f);
            }
        }
    }
}

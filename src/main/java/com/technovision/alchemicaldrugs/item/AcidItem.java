package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.technovision.alchemicaldrugs.AlchemicalDrugsClient.*;

public class AcidItem extends AbstractFoodItem {

    public AcidItem() {
        super("C₂₀H₂₅N₃O");
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            String key = getName().getString();
            if (isLSDEffectEnabled) { cancelThreads(key);}
            isLSDEffectEnabled = true;

            Runnable baseSound = () -> user.playSound(SoundEvents.ENTITY_WARDEN_AMBIENT, 1.5f, 0.8f);
            ScheduledFuture<?> audioThread = executor.scheduleAtFixedRate(baseSound, 3, 2, TimeUnit.SECONDS);
            ScheduledFuture<?> cancelThread = executor.schedule(() -> {
                isLSDEffectEnabled = false;
                audioThread.cancel(false);
            }, 1, TimeUnit.MINUTES);
            setThreads(key, audioThread, cancelThread);
        } else {
            user.getStackInHand(user.getActiveHand()).decrement(1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60 * 20, 0));
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}

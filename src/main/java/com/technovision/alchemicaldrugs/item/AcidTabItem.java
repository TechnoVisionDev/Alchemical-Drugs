package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AcidTabItem extends AbstractFoodItem {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);;

    public AcidTabItem() {
        super(null);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            Runnable baseSound = () -> user.playSound(SoundEvents.ENTITY_WARDEN_AMBIENT, 1.5f, 0.8f);
            ScheduledFuture<?> promise = executor.scheduleAtFixedRate(baseSound, 3, 2, TimeUnit.SECONDS);
            executor.schedule(() -> promise.cancel(true), 1, TimeUnit.MINUTES);
        } else {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 15 * 20, 0));
        }
        return stack;
    }
}

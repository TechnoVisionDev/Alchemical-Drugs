package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

import static com.technovision.alchemicaldrugs.AlchemicalDrugsClient.*;

public class CocaineItem extends AbstractFoodItem {

    public CocaineItem() {
        super("C₁₇H₂₁NO₄");
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 30 * 20, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 30 * 20, 1));
        } else {
            String key = getName().getString();
            if (isCocaineEffectEnabled) { cancelThreads(key); }
            isCocaineEffectEnabled = true;
            setThread(key, executor.schedule(() -> isCocaineEffectEnabled = false, 30, TimeUnit.SECONDS));
        }
        return stack;
    }
}

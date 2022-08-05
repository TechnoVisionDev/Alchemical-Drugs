package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MethItem extends AbstractFoodItem {

    public MethItem() {
        super("C₁₀H₁₅N");
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 30 * 20, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30 * 20, 1));
        }
        return stack;
    }
}

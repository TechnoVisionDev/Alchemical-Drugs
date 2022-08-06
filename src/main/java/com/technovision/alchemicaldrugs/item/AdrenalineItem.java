package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class AdrenalineItem extends AbstractFoodItem {

    public AdrenalineItem() {
        super(null);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.damage(DamageSource.STARVE, 1);
            user.getStackInHand(user.getActiveHand()).decrement(1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20 * 20, 0));
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}

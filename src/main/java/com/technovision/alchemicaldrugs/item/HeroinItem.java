package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

import static com.technovision.alchemicaldrugs.AlchemicalDrugsClient.*;
import static com.technovision.alchemicaldrugs.AlchemicalDrugsClient.isMethEffectEnabled;

public class HeroinItem extends AbstractFoodItem {

    public HeroinItem() {
        super(null);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.damage(DamageSource.STARVE, 1);
            if (!((PlayerEntity) user).isCreative()) user.getStackInHand(user.getActiveHand()).decrement(1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 30 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 30 * 20, 0));
        } else {
            String key = getName().getString();
            if (isHeroinEffectEnabled) { cancelThreads(key); }
            isHeroinEffectEnabled = true;
            setThread(key, executor.schedule(() -> isHeroinEffectEnabled = false, 30, TimeUnit.SECONDS));
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}

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

public class HeroinItem extends AbstractFoodItem {

    public HeroinItem() {
        super(null);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.damage(DamageSource.STARVE, 1);
            PlayerEntity player = (PlayerEntity) user;
            if (!player.isCreative()) user.getStackInHand(user.getActiveHand()).decrement(1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 30 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 30 * 20, 0));
            setWithdrawl(player, 30);
        } else {
            String key = getName().getString();
            if (isHeroinEffectEnabled) { cancelThreads(key); }
            isHeroinEffectEnabled = true;
            setThread(key, executor.schedule(() -> isHeroinEffectEnabled = false, 1, TimeUnit.MINUTES));
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}

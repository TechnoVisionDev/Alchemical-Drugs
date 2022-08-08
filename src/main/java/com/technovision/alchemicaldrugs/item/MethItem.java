package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

import static com.technovision.alchemicaldrugs.AlchemicalDrugsClient.*;

public class MethItem extends AbstractFoodItem {

    public MethItem() {
        super("C₁₀H₁₅N");
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            PlayerEntity player = (PlayerEntity) user;
            if (!player.isCreative()) user.getStackInHand(user.getActiveHand()).decrement(1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 30 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30 * 20, 0));
            setWithdrawl(player, 30);
        } else {
            String key = getName().getString();
            if (isMethEffectEnabled) { cancelThreads(key); }
            isMethEffectEnabled = true;
            setThread(key, executor.schedule(() -> isMethEffectEnabled = false, 30, TimeUnit.SECONDS));
        }
        return stack;
    }
}

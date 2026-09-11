package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CocaineItem extends AbstractFoodItem {

    public CocaineItem(Properties properties) {
        super(properties, "C₁₇H₂₁NO₄");
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            com.technovision.alchemicaldrugs.effect.DrugEffects.start(user, com.technovision.alchemicaldrugs.effect.DrugEffect.COCAINE);

            consume(stack, user);
            user.addEffect(new MobEffectInstance(MobEffects.SPEED, 30 * 20, 2));
            user.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 30 * 20, 1));
            user.addEffect(new MobEffectInstance(MobEffects.HASTE, 30 * 20, 1));
            if (user instanceof Player player) setWithdrawl(player, 30);
        }
        return stack;
    }
}

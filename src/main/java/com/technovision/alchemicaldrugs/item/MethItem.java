package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MethItem extends AbstractFoodItem {

    public MethItem(Properties properties) {
        super(properties, "C₁₀H₁₅N");
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            com.technovision.alchemicaldrugs.effect.DrugEffects.start(user, com.technovision.alchemicaldrugs.effect.DrugEffect.METH);

            consume(stack, user);
            user.addEffect(new MobEffectInstance(MobEffects.SPEED, 30 * 20, 1));
            user.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 30 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 30 * 20, 0));
            if (user instanceof Player player) setWithdrawl(player, 30);
        }
        return stack;
    }
}

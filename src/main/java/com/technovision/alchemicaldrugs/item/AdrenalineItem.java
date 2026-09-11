package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class AdrenalineItem extends AbstractFoodItem {

    public AdrenalineItem(Properties properties) {
        super(properties, null);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            user.hurtServer((net.minecraft.server.level.ServerLevel) world, user.damageSources().starve(), 1);
            consume(stack, user);
            user.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 20 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.HASTE, 20 * 20, 0));
        }
        return stack;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }
}

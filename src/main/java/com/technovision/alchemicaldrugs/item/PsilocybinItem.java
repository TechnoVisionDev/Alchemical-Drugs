package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class PsilocybinItem extends AbstractFoodItem {

    public PsilocybinItem(Properties properties) {
        super(properties, "C₁₂H₁₇(N₂O₄)P");
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            com.technovision.alchemicaldrugs.effect.DrugEffects.start(user, com.technovision.alchemicaldrugs.effect.DrugEffect.SHROOMS);
            consume(stack, user);
            user.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60 * 20, 1));
            user.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.LUCK, 180 * 20, 0));
        }
        return stack;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }
}

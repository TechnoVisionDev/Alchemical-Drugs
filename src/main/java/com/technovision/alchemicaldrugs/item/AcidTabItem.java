package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AcidTabItem extends AbstractFoodItem {

    public AcidTabItem(Properties properties) {
        super(properties, null);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            com.technovision.alchemicaldrugs.effect.DrugEffects.start(user, com.technovision.alchemicaldrugs.effect.DrugEffect.LSD);
            consume(stack, user);
            user.addEffect(new MobEffectInstance(MobEffects.SPEED, 60 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.LUCK, 180 * 20, 0));
        }
        return stack;
    }
}

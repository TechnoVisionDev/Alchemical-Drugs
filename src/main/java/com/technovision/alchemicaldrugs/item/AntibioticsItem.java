package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AntibioticsItem extends AbstractFoodItem {

    public AntibioticsItem(Properties properties) {
        super(properties, null);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            user.removeAllEffects();
            user.heal(2.0f);
            consume(stack, user);
        }
        return stack;
    }
}

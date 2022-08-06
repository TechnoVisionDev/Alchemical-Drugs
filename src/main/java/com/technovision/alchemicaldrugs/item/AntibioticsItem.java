package com.technovision.alchemicaldrugs.item;

import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AntibioticsItem extends AbstractFoodItem {

    public AntibioticsItem() {
        super(null);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.clearStatusEffects();
            user.heal(2.0f);
            if (!((PlayerEntity) user).isCreative()) user.getStackInHand(user.getActiveHand()).decrement(1);
        }
        return stack;
    }
}

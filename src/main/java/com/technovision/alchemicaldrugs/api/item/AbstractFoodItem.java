package com.technovision.alchemicaldrugs.api.item;
import com.technovision.alchemicaldrugs.effect.DrugEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
public abstract class AbstractFoodItem extends ItemWithTooltip {
    public AbstractFoodItem(Properties properties, String formula) { super(properties, formula); }
    @Override public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }
    @Override public ItemUseAnimation getUseAnimation(ItemStack stack) { return ItemUseAnimation.EAT; }
    @Override public int getUseDuration(ItemStack stack, LivingEntity user) { return 32; }
    protected void consume(ItemStack stack, LivingEntity user) {
        if (!(user instanceof Player player) || !player.isCreative()) stack.shrink(1);
    }
    protected void setWithdrawl(Player player, int seconds) { DrugEffects.scheduleWithdrawal(player, seconds * 20); }
}

package com.technovision.alchemicaldrugs.api.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractFoodItem extends ItemWithTooltip {

    private static final Random random = new Random();
    protected static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    public AbstractFoodItem(String chemicalFormula) {
        super(chemicalFormula);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }


    /**
     * Chance to set a random withdrawal effect after a drug has finished.
     * @param player the player to receive the effect.
     * @param secondsTill the second to wait till applying effect.
     */
    public void setWithdrawl(PlayerEntity player, int secondsTill) {
        if (random.nextDouble() >= 0.20) {
            executor.schedule(() -> {
                StatusEffect effect = null;
                switch (random.nextInt(5) + 1) {
                    case 1 -> effect = StatusEffects.WEAKNESS;
                    case 2 -> effect = StatusEffects.SLOWNESS;
                    case 3 -> effect = StatusEffects.MINING_FATIGUE;
                    case 4 -> effect = StatusEffects.HUNGER;
                    case 5 -> effect = StatusEffects.NAUSEA;
                }
                player.addStatusEffect(new StatusEffectInstance(effect, 30 * 20, 0));
            }, secondsTill, TimeUnit.SECONDS);
        }
    }
}


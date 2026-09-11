package com.technovision.alchemicaldrugs.effect;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
public final class DrugEffects {
    private static final List<Withdrawal> PENDING = new ArrayList<>();
    private record Withdrawal(UUID player, int deadline, Holder<MobEffect> effect) { }
    public static void initialize() {
        PayloadTypeRegistry.clientboundPlay().register(DrugEffectPayload.TYPE, DrugEffectPayload.CODEC);
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var iterator = PENDING.iterator();
            while (iterator.hasNext()) {
                var task = iterator.next();
                var player = server.getPlayerList().getPlayer(task.player());
                if (player == null || !player.isAlive()) { iterator.remove(); continue; }
                if (server.getTickCount() >= task.deadline()) {
                    player.addEffect(new MobEffectInstance(task.effect(), 600, 0));
                    iterator.remove();
                }
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> PENDING.removeIf(task -> task.player().equals(handler.player.getUUID())));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> PENDING.clear());
    }
    public static void start(LivingEntity user, DrugEffect effect) {
        if (user instanceof ServerPlayer player) ServerPlayNetworking.send(player, new DrugEffectPayload(effect.ordinal()));
    }
    public static void scheduleWithdrawal(Player user, int delayTicks) {
        if (!(user instanceof ServerPlayer player)) return;
        // Preserve the original >= 0.15 roll: an 85% withdrawal chance.
        if (player.getRandom().nextDouble() < 0.15) return;
        Holder<MobEffect> effect = switch (player.getRandom().nextInt(4)) {
            case 0 -> MobEffects.WEAKNESS;
            case 1 -> MobEffects.SLOWNESS;
            case 2 -> MobEffects.MINING_FATIGUE;
            default -> MobEffects.HUNGER;
        };
        PENDING.add(new Withdrawal(player.getUUID(), player.level().getServer().getTickCount() + delayTicks, effect));
    }
}

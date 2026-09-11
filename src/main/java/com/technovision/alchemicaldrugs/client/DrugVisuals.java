package com.technovision.alchemicaldrugs.client;
import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.effect.DrugEffect;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import java.util.EnumMap;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.sounds.SoundEvents;
public final class DrugVisuals {
    private static final EnumMap<DrugEffect, Integer> REMAINING = new EnumMap<>(DrugEffect.class);
    public static void start(DrugEffect effect) { REMAINING.put(effect, effect.durationTicks); }
    public static boolean isActive(DrugEffect effect) { return REMAINING.containsKey(effect); }
    public static void clear() { REMAINING.clear(); }
    public static void tick(Minecraft client) {
        if (client.player == null || client.level == null || !client.player.isAlive()) { clear(); return; }
        if (client.isPaused()) return;
        var iterator = REMAINING.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            int remaining = entry.getValue() - 1;
            int elapsed = entry.getKey().durationTicks - remaining;
            if (entry.getKey().hallucinationAudio && elapsed >= 60 && (elapsed - 60) % 40 == 0 && remaining > 0)
                client.player.playSound(SoundEvents.WARDEN_AMBIENT, 1.5f, 0.8f);
            if (remaining <= 0) iterator.remove(); else entry.setValue(remaining);
        }
    }
    public static void render(Minecraft client, RenderTarget target, GraphicsResourceAllocator allocator) {
        if (client.level == null || client.player == null) return;
        for (DrugEffect effect : DrugEffect.values()) {
            if (!isActive(effect)) continue;
            PostChain chain = client.getShaderManager().getPostChain(AlchemicalDrugs.id(effect.path()), Set.of(PostChain.MAIN_TARGET_ID));
            if (chain != null) chain.process(target, allocator);
        }
    }
}
